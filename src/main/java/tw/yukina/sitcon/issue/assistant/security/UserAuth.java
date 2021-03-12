package tw.yukina.sitcon.issue.assistant.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tw.yukina.sitcon.issue.assistant.manager.AuthManager;

import java.util.Collection;
import java.util.HashSet;

public class UserAuth implements UserDetails {

    private final String username;
    private final AuthManager authManager;

    public UserAuth(String username, AuthManager authManager){
        this.username = username;
        this.authManager = authManager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "User";
            }
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return authManager.getTempPassword(username);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
