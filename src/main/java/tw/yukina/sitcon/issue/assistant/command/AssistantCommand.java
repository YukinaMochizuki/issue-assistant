package tw.yukina.sitcon.issue.assistant.command;

import tw.yukina.sitcon.issue.assistant.constants.Role;

public interface AssistantCommand {
    public String getCommandName();
    public Role[] getPermissions();
}
