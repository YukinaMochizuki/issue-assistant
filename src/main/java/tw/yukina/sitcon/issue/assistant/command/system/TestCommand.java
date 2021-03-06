package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.command.Command;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;
import tw.yukina.sitcon.issue.assistant.constants.Role;

import java.util.List;

@Component
public class TestCommand implements Command {
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.COMMAND;
    }

    @Override
    public SendMessage exec(Update update, List<String> parameter) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(String.valueOf(parameter));

        return sendMessage;
    }
}
