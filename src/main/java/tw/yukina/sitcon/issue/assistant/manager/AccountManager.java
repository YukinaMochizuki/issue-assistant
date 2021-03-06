package tw.yukina.sitcon.issue.assistant.manager;

import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.GroupRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.Set;

@Component
public class AccountManager {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public AccountManager(UserRepository userRepository, GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public User getUserByTG(int telegramUserId){
        return userRepository.findByTelegramUserId(telegramUserId);
    }

    public User getUserByGitLab(int gitLabUserId){
        return userRepository.findByGitLabUserId(gitLabUserId);
    }

    public Set<User> getAllUser(Role role){
        return userRepository.findAllByRole(role);
    }
}
