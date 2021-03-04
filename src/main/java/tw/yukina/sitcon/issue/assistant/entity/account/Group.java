package tw.yukina.sitcon.issue.assistant.entity.account;

import lombok.Getter;
import lombok.Setter;
import tw.yukina.sitcon.issue.assistant.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity(name = "chat_group")
@Getter
@Setter
public class Group extends AbstractEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private String chatId;
}
