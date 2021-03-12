package tw.yukina.sitcon.issue.assistant.entity.account;

import lombok.*;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Locale;

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

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private UserCache userCache;

    public String getTelegramUserName(){
        if(userCache == null)return "";
        String tgUsername = userCache.getTelegramUserName();
        return tgUsername != null ? "@" + userCache.getTelegramUserName() : "";
    }

    public String getDisplayName(){
        if(userCache == null)return "";
        String display = userCache.getDisplayName();
        return display != null ? display : "";
    }

}
