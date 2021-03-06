package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

@Component
public class TestAssistantCommand extends AbstractAssistantCommand implements Runnable{
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }

    @Override
    public void run() {
        SendMessage sendMessage = MessageSupplier.getMarkdownFormat();
        sendMessage.setChatId(String.valueOf(getChatId()));
        sendMessage.setText("You are the best!");

        getTelegramConfig().sendMessage(sendMessage);
    }
}
