package tw.yukina.sitcon.issue.assistant.entity.account;

import lombok.Getter;
import lombok.Setter;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class User extends AbstractEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private String telegramUserId;

    @NotNull
    @Column(unique = true)
    private String gitLabUserId;

    @NotNull
    private Role role;

    @Column(unique = true)
    private Long chatId;
}
