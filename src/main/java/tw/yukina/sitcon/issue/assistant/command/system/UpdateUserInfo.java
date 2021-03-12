package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.manager.telegram.TelegramUserInfoManager;
import tw.yukina.sitcon.issue.assistant.repository.UserCacheRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

@Component
@Command(name = "/updateUserInfo", description = "Get your telegram user id")
public class UpdateUserInfo extends AbstractAssistantCommand implements Runnable{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Autowired
    private TelegramUserInfoManager telegramUserInfoManager;

    @Override
    public void run() {
        User user = userRepository.findByTelegramUserId(getChatId());
        if(user != null) {
            sendMessageToChatId("Updating your user info");
            telegramUserInfoManager.updateCache(user);
            sendMessageToChatId("Your user info is " + userCacheRepository.findByUser(user).toString());
        }
    }

    @Override
    public String getCommandName() {
        return "updateUserInfo";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
