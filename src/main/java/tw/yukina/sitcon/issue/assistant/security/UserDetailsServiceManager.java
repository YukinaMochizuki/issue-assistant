package tw.yukina.sitcon.issue.assistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.manager.AuthManager;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

@Component
public class UserDetailsServiceManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Lazy
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private AuthManager authManager;

    public UserDetailsServiceManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByName(username) == null)throw new UsernameNotFoundException(username);
        return new UserAuth(username, authManager);
    }
}
