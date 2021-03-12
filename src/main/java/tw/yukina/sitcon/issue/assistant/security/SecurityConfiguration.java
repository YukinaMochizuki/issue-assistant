package tw.yukina.sitcon.issue.assistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsServiceManager userDetailsServiceManager) throws Exception {
        auth.userDetailsService(userDetailsServiceManager);
//        UserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        userDetailsService.createUser(new User("yukina", passwordencoder().encode("12345"), new HashSet<>()));
//        auth.userDetailsService(userDetailsService);
    }

    @Configuration
    @Order(1)
    public static class RestSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                    // make sure to only make rules for /api/**  - everything else will be defined in WebSecurityConfigurationAdapter
                    .antMatcher("/webhook/**").authorizeRequests().anyRequest().permitAll();
        }
    }

    @Override
    @Order(2)
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()

                // Register our CustomRequestCache that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new ExternalHttpSessionRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)

                // Register the success handler that redirects users to the root page
                .successHandler(new SimpleUrlAuthenticationSuccessHandler()  {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        getRedirectStrategy().sendRedirect(request, response, "/");
                    }
                })

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources //
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest //
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // (development mode) static resources //
                "/frontend/**",

                // (development mode) webjars //
                "/webjars/**",

                // (production mode) static resources //
                "/frontend-es5/**", "/frontend-es6/**");
    }

    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }

}
