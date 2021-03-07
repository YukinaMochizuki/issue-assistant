package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

@Command(name = "find", description = "Find users and display user info")
public class findUser extends AbstractSubCommand implements Runnable{

    @Parameters(index = "0", paramLabel = "<name>")
    private String name;

    @Override
    public void run() {
        User user = userCommand.getUserRepository().findByName(name);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user named " + name);
        else userCommand.sendMessageToChatId(user.toString());
    }
}
