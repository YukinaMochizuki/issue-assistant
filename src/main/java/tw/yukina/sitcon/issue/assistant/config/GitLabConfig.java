package tw.yukina.sitcon.issue.assistant.config;

import lombok.SneakyThrows;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.IssuesApi;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitLabConfig {

    @Value("${gitlab.token}")
    private String gitLabToken;

    @Value("${gitlab.namespace}")
    private String namespace;

    @Value("${gitlab.project}")
    private String project;

    @Bean
    public GitLabApi getGitLabApi(){
        return new GitLabApi("https://gitlab.com/", gitLabToken);
    }

    @Bean
    public IssuesApi getIssuesApi(GitLabApi gitLabApi){
        return gitLabApi.getIssuesApi();
    }

    @Bean
    public Project getProject(GitLabApi gitLabApi) throws GitLabApiException {
        return gitLabApi.getProjectApi().getProject(namespace, project);
    }
}
