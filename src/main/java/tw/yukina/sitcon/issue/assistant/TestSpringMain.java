package tw.yukina.sitcon.issue.assistant;

import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tw.yukina.sitcon.issue.assistant.command.AssistantCommand;
import tw.yukina.sitcon.issue.assistant.command.system.HelpCommand;
import tw.yukina.sitcon.issue.assistant.command.system.TestPicocliCommand;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

import java.io.*;
import java.util.Set;

@Component
public class TestSpringMain {

    final GitLabApi gitLabApi;

    @Autowired
    Set<AssistantCommand> assistantCommandSet;

    @Autowired
    TestPicocliCommand testPicocliCommand;

    @Autowired
    TelegramConfig telegramConfig;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private HelpCommand helpCommand;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TestSpringMain(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @EventListener
    public void onApplicationStart(ApplicationReadyEvent applicationReadyEvent){
        telegramConfig.sendMessage(MessageSupplier.getMarkdownFormatBuilder().text("test").chatId("240322569").build());
    }
}
