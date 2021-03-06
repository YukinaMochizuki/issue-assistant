package tw.yukina.sitcon.issue.assistant.command.system;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.command.Command;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;
import tw.yukina.sitcon.issue.assistant.constants.Role;

import java.util.List;

public class AddUser implements Command {
    @Override
    public String getCommandName() {
        return "addUser";
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
        return null;
    }
}
