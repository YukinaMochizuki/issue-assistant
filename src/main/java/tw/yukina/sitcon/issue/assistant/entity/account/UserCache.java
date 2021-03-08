package tw.yukina.sitcon.issue.assistant.entity.account;

import lombok.*;
import tw.yukina.sitcon.issue.assistant.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCache extends AbstractEntity {
    @NotNull
    @OneToOne
    @ToString.Exclude
    private User user;
    private String telegramUserName;
    private String displayName; //First name + Last name
    private String email;
}
