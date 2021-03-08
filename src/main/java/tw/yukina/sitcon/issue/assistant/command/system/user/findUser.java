package tw.yukina.sitcon.issue.assistant.command.system.user;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractSubCommand;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.entity.account.UserCache;

@Command(name = "find", description = "Find users and display user info")
public class findUser extends AbstractSubCommand {

    @Command(name = "name", description = "Display name")
    private void findByName(@Parameters(index = "0", paramLabel = "<name>") String name) {
        User user = userCommand.getUserRepository().findByName(name);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user named " + name);
        else displayUserInfo(user);
    }

    @Command(name = "TGid", description = "Telegram user id")
    private void findByTgId(@Parameters(index = "0", paramLabel = "<id>") int id) {
        if(id == 0){
            userCommand.sendMessageToChatId("Invalid value : parameter cannot be 0");
            return;
        }

        User user = userCommand.getUserRepository().findByTelegramUserId(id);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user id is " + id);
        else displayUserInfo(user);
    }

    @Command(name = "GLid", description = "GitLab user id")
    private void findByGitLabId(@Parameters(index = "0", paramLabel = "<id>") int id) {
        if(id == 0){
            userCommand.sendMessageToChatId("Invalid value : parameter cannot be 0");
            return;
        }

        User user = userCommand.getUserRepository().findByGitLabUserId(id);
        if(user == null)userCommand.sendMessageToChatId("Can not find any user GitLab id is " + id);
        else displayUserInfo(user);
    }

    private void displayUserInfo(User user){
        userCommand.sendMessageToChatId("User Setting is " + user.toString());

        UserCache userCache = userCommand.getUserCacheRepository().findByUser(user);
        if(userCache != null) userCommand.sendMessageToChatId("User Profile is " +
                userCommand.getUserCacheRepository().findByUser(user).toString());
        else userCommand.sendMessageToChatId("And user profile has not been initialized");
    }
}
