package tw.yukina.sitcon.issue.assistant.entity;

import lombok.*;
import tw.yukina.sitcon.issue.assistant.constants.WebhookAction;
import tw.yukina.sitcon.issue.assistant.entity.account.Group;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitLabWebhookFilter extends AbstractEntity{

    @NotNull
    private String objectKind; // issue, note(plan)
    private String eventType; //todo
    private int objectId; //todo
    private int authorId; //todo

    @NotNull
    private String action; // open, update, close, reopen
    private String updateType; // changes/assignees  or changes/labels

    private String label;
    private int assigneeId; //todo

    @OneToOne
    private User user;

    @OneToOne
    private Group group;

    private WebhookAction webhookAction; //todo
}
