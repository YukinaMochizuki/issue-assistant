package tw.yukina.sitcon.issue.assistant.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.sitcon.issue.assistant.command.Command;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.constants.CommandType;

import java.util.ArrayList;
import java.util.Set;

@Component
public class TelegramManager {

    private final Set<Command> commands;

    private final TelegramPermissionManager telegramPermissionManager;

    @Lazy
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private TelegramConfig telegramConfig;

    public TelegramManager(Set<Command> command, TelegramPermissionManager telegramPermissionManager) {
        this.commands = command;
        this.telegramPermissionManager = telegramPermissionManager;
    }

    public void messageInput(Update update){

        String message = update.getMessage().getText();

        if(message.startsWith("/")){
            ArrayList<String> parameter = new ArrayList<>();
            if(message.contains(" ")){
                boolean doFirst = true;
                int index_start = 0;
                int index_end = 0;
                while (true){
                    index_start = message.indexOf(" ",index_start);

                    if(doFirst){
                        parameter.add(message.substring(1,index_start));
                        doFirst = false;
                    }

                    if(index_start == -1)break;
                    else {
                        if(message.indexOf(" ",index_start + 1) != -1){
                            index_end = message.indexOf(" ",index_start + 1);
                            parameter.add(message.substring(index_start + 1,index_end));
                            index_start++;
                        }else {
                            parameter.add(message.substring(index_start + 1));
                            index_start++;
                        }
                    }
                }
            }else {
                parameter.add(message.substring(1));
            }

            System.out.println(parameter.toString());

            for(Command command: commands){
                System.out.println(command.getCommandName());

                if(command.getCommandName().compareTo(parameter.get(0)) == 0 &&
                        command.getCommandType().compareTo(CommandType.COMMAND) == 0){
                    if(update.getMessage().getChat().isUserChat() &&
                            telegramPermissionManager.checkUser(update.getMessage().getFrom().getId(), command)){
                        telegramConfig.sendMessage(command.exec(update, parameter));
                    }
                }
            }

        }//else conversationManager.communicate(input);


    }
}
