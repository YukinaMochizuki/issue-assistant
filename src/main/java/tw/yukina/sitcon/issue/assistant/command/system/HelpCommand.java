package tw.yukina.sitcon.issue.assistant.command.system;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.AbstractAssistantCommand;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

import java.util.concurrent.Callable;

@Component
@Command(name = "help", version = "help 4.0")
public class HelpCommand extends AbstractAssistantCommand implements Runnable{

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.Guest};
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
