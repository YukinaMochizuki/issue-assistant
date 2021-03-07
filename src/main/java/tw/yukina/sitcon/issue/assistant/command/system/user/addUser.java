package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

@Command(name = "add", description = "Add a user and give it permissions")
public class addUser extends AbstractSubCommand implements Runnable{

    @Parameters(index = "0", paramLabel = "<name>", description = "Please use a name that everyone knows")
    private String name;

    @Parameters(index = "1", paramLabel = "<userid>", description = "Use /getTelegramUserId to get your user id")
    private int userId;

    @Parameters(index = "2", paramLabel = "<role>", description = "Valid values: ${COMPLETION-CANDIDATES}")
    private Role role;

    @Override
    public void run() {
        if(userCommand.getUserRepository().findByName(name) != null){
            userCommand.sendMessageToChatId("User "+ name + " already in the database");
            return;
        }else if(userCommand.getUserRepository().findByTelegramUserId(userId) != null){
            userCommand.sendMessageToChatId("User id "+ userId + " already in the database");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setTelegramUserId(userId);
        user.setRole(role);

        userCommand.getUserRepository().save(user);
        userCommand.sendMessageToChatId("User "+ name + " has been saved to the database");
    }
}
