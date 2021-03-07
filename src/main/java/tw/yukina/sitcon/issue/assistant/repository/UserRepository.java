package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByGitLabUserId(int username);
    User findByTelegramUserId(int username);
    Set<User> findAllByRole(Role role);
}