package tw.yukina.sitcon.issue.assistant.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label extends AbstractEntity{

    private String name;

    @ManyToMany(mappedBy = "labels", fetch = FetchType.EAGER)
    private Set<GitLabWebhookFilter> gitLabWebhookFilterSet = new HashSet<>();

}
