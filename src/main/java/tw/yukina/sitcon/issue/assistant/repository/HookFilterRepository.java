package tw.yukina.sitcon.issue.assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.yukina.sitcon.issue.assistant.constants.WebhookAction;
import tw.yukina.sitcon.issue.assistant.entity.GitLabWebhookFilter;
import tw.yukina.sitcon.issue.assistant.entity.account.Group;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Repository
public interface HookFilterRepository extends JpaRepository<GitLabWebhookFilter, Long> {

    Set<GitLabWebhookFilter> findAllByObjectId(int id);
    Set<GitLabWebhookFilter> findAllByObjectKind(String objectKind);
    Set<GitLabWebhookFilter> findAllByAuthorId(int id);
    Set<GitLabWebhookFilter> findAllByEventType(String eventType);

    Set<GitLabWebhookFilter> findAllByAction(String action);
    Set<GitLabWebhookFilter> findAllByUpdateType(String updateType);

    Set<GitLabWebhookFilter> findAllByLabel(String label);
    Set<GitLabWebhookFilter> findAllByAssigneeId(int assigneeId);

    Set<GitLabWebhookFilter> findAllByUser(User user);
    Set<GitLabWebhookFilter> findAllByGroup(Group group);

    Set<GitLabWebhookFilter> findAllByWebhookAction(WebhookAction webhookAction);
}
