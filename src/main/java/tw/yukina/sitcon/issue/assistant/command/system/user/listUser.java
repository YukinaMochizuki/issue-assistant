package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

import java.util.List;

@Command(name = "list", description = "List all users")
public class listUser extends AbstractSubCommand implements Runnable{
    @Override
    public void run() {
        List<User> users = userCommand.getUserRepository().findAll();
        if(users.isEmpty())userCommand.sendMessageToChatId("Can not find any users in database");
        else users.forEach(user -> userCommand.sendMessageToChatId(user.getName()));
    }
}
