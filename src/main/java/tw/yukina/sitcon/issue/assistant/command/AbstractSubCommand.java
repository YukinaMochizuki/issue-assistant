package tw.yukina.sitcon.issue.assistant.command;

import lombok.Getter;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import tw.yukina.sitcon.issue.assistant.command.system.user.UserCommand;

@Command
@Getter
public abstract class AbstractSubCommand {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    public boolean usageHelpRequested;

    @ParentCommand
    public UserCommand userCommand;
}
