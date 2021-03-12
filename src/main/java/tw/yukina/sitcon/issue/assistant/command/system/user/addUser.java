package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Option;
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

    @Option(names = {"-gid", "--GitLabUserId"}, paramLabel = "<GitLabUserId>")
    private int gitLabId;

    @Override
    public void run() {
        if(parentCommand.getUserRepository().findByName(name) != null){
            parentCommand.sendMessageToChatId("User "+ name + " already in the database");
            return;
        }else if(parentCommand.getUserRepository().findByTelegramUserId(userId) != null){
            parentCommand.sendMessageToChatId("User id "+ userId + " already in the database");
            return;
        }else if(gitLabId != 0 && parentCommand.getUserRepository().findByGitLabUserId(gitLabId) != null){
            parentCommand.sendMessageToChatId("GitLab id "+ gitLabId + " already in the database");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setTelegramUserId(userId);
        user.setRole(role);
        user.setGitLabUserId(gitLabId);

        parentCommand.getUserRepository().save(user);
        parentCommand.sendMessageToChatId("User "+ name + " has been saved to the database");
    }
}
