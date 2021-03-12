package tw.yukina.sitcon.issue.assistant.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecentNotification extends AbstractEntity{
    private LocalDateTime localDateTime;
    private String message;

    @OneToOne
    private GitLabWebhookFilter gitLabWebhookFilter;

    public String getFilterName(){
        return gitLabWebhookFilter.getName();
    }

    public String getDateTime(){
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime);
    }
}
