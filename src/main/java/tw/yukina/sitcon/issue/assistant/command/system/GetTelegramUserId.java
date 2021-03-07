package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;

@Component
public class GetTelegramUserId extends AbstractAssistantCommand implements Runnable {

    @Override
    public String getCommandName() {
        return "getTelegramUserId";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.Guest};
    }

    @Override
    public void run() {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(getChatId()));
        message.setText("Your user id is " + getChatId());

        getTelegramConfig().sendMessage(message);
    }
}