package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByGitLabUserId(String username);
    User findByTelegramUserId(String username);
    User findByChatId(Long chatId);
    Set<User> findAllByRole(Role role);
}