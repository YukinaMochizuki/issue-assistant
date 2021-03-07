package tw.yukina.sitcon.issue.assistant.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import picocli.CommandLine;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.command.system.HelpCommand;
import tw.yukina.sitcon.issue.assistant.command.system.TestPicocliCommand;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Component
public class TelegramManager {

    private final Set<AbstractAssistantCommand> assistantCommands;

    private final TelegramPermissionManager telegramPermissionManager;

    @Lazy
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private TelegramConfig telegramConfig;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private TestPicocliCommand testPicocliCommand;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private HelpCommand helpCommand;

    public TelegramManager(Set<AbstractAssistantCommand> assistantCommands, TelegramPermissionManager telegramPermissionManager) {
        this.assistantCommands = assistantCommands;
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

            Optional<AbstractAssistantCommand> assistantCommandOptional =
                    assistantCommands.stream().filter(command -> parameter.get(0).equals(command.getCommandName())).findAny();

            if(assistantCommandOptional.isPresent()) {
                AbstractAssistantCommand assistantCommand = assistantCommandOptional.get();

                if(update.getMessage().getChat().isUserChat() &&
                        telegramPermissionManager.checkUser(update.getMessage().getFrom().getId(), assistantCommand)) {

                    StringWriter out = new StringWriter();
                    PrintWriter writer = new PrintWriter(out);

                    parameter.remove(0);
                    parameter.add(0, "--ChatId");
                    parameter.add(1, String.valueOf(update.getMessage().getFrom().getId()));
                    String[] args = parameter.toArray(new String[0]);

                    new CommandLine(assistantCommand).setOut(writer).setErr(writer)
                            .setCaseInsensitiveEnumValuesAllowed(true)
                            .setUsageHelpWidth(100).execute(args);

                    String result = out.toString();
                    if (!result.isEmpty())
                        telegramConfig.sendMessage(MessageSupplier.getMarkdownFormatBuilder()
                                .chatId(parameter.get(1))
                                .text(result)
                                .build());

                    writer.close();
                }
            }else {
                telegramConfig.sendMessage(MessageSupplier.getMarkdownFormatBuilder()
                        .chatId(String.valueOf(update.getMessage().getFrom().getId()))
                        .text("Command Not Found, please type /help for usage info.")
                        .build());
            }
        }//else conversationManager.communicate(input);
    }
}
