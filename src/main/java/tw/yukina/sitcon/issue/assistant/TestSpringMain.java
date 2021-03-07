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
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private HelpCommand helpCommand;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TestSpringMain(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @EventListener
    public void onApplicationStart(ApplicationReadyEvent applicationReadyEvent){
//        try {
//            Project project = gitLabApi.getProjectApi().getProject("sitcon-tw", "Test");
//
//            logger.info(String.valueOf(project.getOpenIssuesCount()));
//
//        } catch (GitLabApiException e) {
//            e.printStackTrace();
//        }

//        for(AbstractCommand command: commandSet){
//            Class<?> commandClass = command.getClass();
//
//            System.out.println(commandClass.getSimpleName());
//            System.out.println(Arrays.toString(commandClass.getMethods()));
//
//        }


        StringWriter out   = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

//        writer.flush(); // flush is really optional here, as Writer calls the empty StringWriter.flush

        new CommandLine(testPicocliCommand).setOut(writer).setErr(writer).execute(new String[]{"--ChatId", "240322569"});

        String result = out.toString();

//        System.out.println(result);

        writer.close();
    }
}
