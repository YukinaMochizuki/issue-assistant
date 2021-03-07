package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

@Component
@Command(name = "help", version = "help 4.0")
public class HelpCommand extends AbstractAssistantCommand implements Runnable{

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }

    @Override
    public void run() {
        String message = "help me plz";

        getTelegramConfig().sendMessage(MessageSupplier.getMarkdownFormatBuilder()
                .chatId(String.valueOf(getChatId()))
                .text(message)
                .build());
    }
}
