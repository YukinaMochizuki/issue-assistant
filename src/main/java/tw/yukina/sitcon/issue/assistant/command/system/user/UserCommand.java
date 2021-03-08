package tw.yukina.sitcon.issue.assistant.command.system.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.repository.UserCacheRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

@Component
@Command(name = "/user", subcommands = {CommandLine.HelpCommand.class, addUser.class, findUser.class,
        listUser.class, delUser.class})
@Getter
public class UserCommand extends AbstractAssistantCommand {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Override
    public String getCommandName() {
        return "user";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }
}
