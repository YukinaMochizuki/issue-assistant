package tw.yukina.sitcon.issue.assistant.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.manager.AuthManager;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

@Component
public class UserDetailsServiceManager implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthManager authManager;

    public UserDetailsServiceManager(UserRepository userRepository, AuthManager authManager) {
        this.userRepository = userRepository;
        this.authManager = authManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByName(username) == null)throw new UsernameNotFoundException(username);
        return new UserAuth(username, authManager);
    }
}
