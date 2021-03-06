package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.command.AssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.List;

public class AddUser implements AssistantCommand {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getCommandName() {
        return "addUser";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }

//    @Override
//    public CommandType getCommandType() {
//        return CommandType.COMMAND;
//    }
//
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
//
//
//
//        return null;
//    }
}
