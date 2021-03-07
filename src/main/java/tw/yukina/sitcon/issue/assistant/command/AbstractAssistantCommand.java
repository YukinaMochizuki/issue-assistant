package tw.yukina.sitcon.issue.assistant.command;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;

@Command(mixinStandardHelpOptions = true)
@Getter
public abstract class AbstractAssistantCommand implements AssistantCommand {
    @Option(hidden = true, required = true, names = {"--ChatId"}, description = "Valid values: ${COMPLETION-CANDIDATES}")
    private int chatId;

    @Lazy
    @Autowired
    private TelegramConfig telegramConfig;
}
