package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.entity.account.UserCache;

public interface UserCacheRepository extends JpaRepository<UserCache, Long> {
    UserCache findByUser(User user);
}
