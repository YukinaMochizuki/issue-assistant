package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.manager.telegram.TelegramUserInfoManager;

@Component
@Command(name = "start", description = "Update your user info")
public class Start extends AbstractAssistantCommand implements Runnable{

    @Autowired
    private TelegramUserInfoManager telegramUserInfoManager;

    @Override
    public void run() {
        sendMessageToChatId("Your telegram user id is " + getChatId());
        sendMessageToChatId("If you want to get some help, please send /help for me");
    }

    @Override
    public String getCommandName() {
        return "start";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
