package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.sitcon.issue.assistant.entity.RecentNotification;

public interface RecentNotificationRepository extends JpaRepository<RecentNotification, Long> {
}
