package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

@Command(name = "findById", description = "Find users and display detail")
public class findUserById extends AbstractSubCommand implements Runnable{

    @Parameters(index = "0", paramLabel = "<id>")
    private int id;

    @Override
    public void run() {
        User user = userCommand.getUserRepository().findByTelegramUserId(id);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user id is " + id);
        else userCommand.sendMessageToChatId(user.toString());
    }
}
