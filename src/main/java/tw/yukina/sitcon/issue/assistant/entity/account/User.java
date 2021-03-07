package tw.yukina.sitcon.issue.assistant.entity.account;

import lombok.*;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private int telegramUserId;

    @Column(unique = true)
    private int gitLabUserId;

    @NotNull
    private Role role;

}
