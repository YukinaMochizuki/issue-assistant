package tw.yukina.sitcon.issue.assistant.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;
import tw.yukina.sitcon.issue.assistant.constants.Role;

import java.util.List;

public interface AssistantCommand {
    public String getCommandName();
    public Role[] getPermissions();
}
