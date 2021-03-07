package tw.yukina.sitcon.issue.assistant.command.system.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.command.AssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.List;

@Component
@Command(name = "user", subcommands = {CommandLine.HelpCommand.class, addUser.class})
@Getter
public class UserCommand extends AbstractAssistantCommand {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getCommandName() {
        return "user";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }


//    @Override
//    public SendMessage exec(Update update, List<String> parameter) {
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//
//        User user = new User();
//        user.setTelegramUserId(Integer.parseInt(parameter.get(2)));
//
//        if(parameter.get(1).compareTo("admin") == 0) user.setRole(Role.ADMIN);
//        if(parameter.get(1).compareTo("member") == 0) user.setRole(Role.MEMBER);
//        return null;
//    }
}
