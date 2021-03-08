package tw.yukina.sitcon.issue.assistant;

import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.command.AssistantCommand;
import tw.yukina.sitcon.issue.assistant.command.system.TestPicocliCommand;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.GitLabWebhookFilter;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.HookFilterRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

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
    private HookFilterRepository hookFilterRepository;

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TestSpringMain(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @EventListener
    public void onApplicationStart(ApplicationReadyEvent applicationReadyEvent){
//        telegramConfig.sendMessage(MessageSupplier.getMarkdownFormatBuilder().text("test\ntest2").chatId("240322569").build());
//
//        User user = new User();
//        user.setTelegramUserId(240322569);
//        user.setRole(Role.ADMIN);
//        user.setName("Yukina");
//
//        userRepository.save(user);
//
//        GitLabWebhookFilter gitLabWebhookFilter = GitLabWebhookFilter.builder()
//                .objectKind("issue")
//                .action("update")
//                .label("To Do")
//                .user(userRepository.findByName("Yukina"))
//                .build();
//
//        hookFilterRepository.save(gitLabWebhookFilter);
    }
}
