package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.yukina.sitcon.issue.assistant.entity.account.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByChatId(int id);
}
