package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

@Command(name = "del", description = "Find users and delete it")
public class delUser extends AbstractSubCommand implements Runnable {

    @Parameters(index = "0", paramLabel = "<name>")
    private String name;

    @Override
    public void run() {
        User user = userCommand.getUserRepository().findByName(name);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user named " + name);
        else {
            userCommand.getUserRepository().delete(user);
            userCommand.sendMessageToChatId("Delete user " + name);
        }
    }
}
