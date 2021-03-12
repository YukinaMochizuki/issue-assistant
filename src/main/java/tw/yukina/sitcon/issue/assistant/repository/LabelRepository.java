package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.sitcon.issue.assistant.entity.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
