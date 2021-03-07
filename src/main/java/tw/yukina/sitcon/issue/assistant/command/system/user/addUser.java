package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;

@Command(name = "add", description = "Add user to the repository and give it permissions", mixinStandardHelpOptions = true)
public class addUser implements Runnable{

    @Parameters(index = "0", paramLabel = "<display name>", description = "Please use a name that everyone knows")
    private String name;

    @Parameters(index = "1", paramLabel = "<userid>", description = "Use /getTelegramUserId to get your user id")
    private int userId;

    @Parameters(index = "2", paramLabel = "<role>", description = "Valid values: ${COMPLETION-CANDIDATES}")
    private Role role;

    @ParentCommand
    private UserCommand userCommand;

    @Override
    public void run() {
        User user = new User();
        user.setName(name);
        user.setTelegramUserId(userId);
        user.setRole(role);

        userCommand.getUserRepository().save(user);

    }
}
