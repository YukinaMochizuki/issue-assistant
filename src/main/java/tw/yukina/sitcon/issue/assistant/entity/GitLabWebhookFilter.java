package tw.yukina.sitcon.issue.assistant.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import tw.yukina.sitcon.issue.assistant.constants.WebhookAction;
import tw.yukina.sitcon.issue.assistant.entity.account.Group;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("JpaDataSourceORMInspection")
public class GitLabWebhookFilter extends AbstractEntity{

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String objectKind; // issue
    private String eventType; //todo
    private int objectId; //todo
    private int authorId; //todo

    @NotNull
    private String action; // open, update, close, reopen, comment
    private String updateType; // changes/assignees  or changes/labels

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "GitLabWebhookFilter_labels",
            joinColumns = { @JoinColumn(name = "GitLabWebhookFilter_id") },
            inverseJoinColumns = { @JoinColumn(name = "Label_id") })
    private Set<Label> labels = new HashSet<>();
    private int assigneeId; //todo

    @OneToOne
    private User user;

    @OneToOne
    private Group group;

    private WebhookAction webhookAction; //todo
}
