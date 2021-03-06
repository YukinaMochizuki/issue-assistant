package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.command.Command;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;
import tw.yukina.sitcon.issue.assistant.constants.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetTelegramUserId implements Command {

    @Override
    public String getCommandName() {
        return "getTelegramUserId";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN, Role.MEMBER, Role.Guest};
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.COMMAND;
    }

    @Override
    public SendMessage exec(Update update, List<String> parameter) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Your user id is " + String.valueOf(update.getMessage().getFrom().getId()));
        return message;
    }
}