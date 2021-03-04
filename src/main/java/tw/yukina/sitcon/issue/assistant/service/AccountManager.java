package tw.yukina.sitcon.issue.assistant.service;

import org.springframework.stereotype.Service;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.GroupRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.Set;

@Service
public class AccountManager {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public AccountManager(UserRepository userRepository, GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public User getUserByTG(String telegramUserId){
        return userRepository.findByTelegramUserId(telegramUserId);
    }

    public User getUserByGitLab(String gitLabUserId){
        return userRepository.findByGitLabUserId(gitLabUserId);
    }

    public Set<User> getAllUser(Role role){
        return userRepository.findAllByRole(role);
    }


}
