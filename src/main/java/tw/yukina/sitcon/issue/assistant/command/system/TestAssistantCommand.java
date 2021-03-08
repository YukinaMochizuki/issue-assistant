package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;

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
        sendMessageToChatId("TestMessage");
    }
}
