package tw.yukina.sitcon.issue.assistant.service;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.entity.GitLabWebhookFilter;
import tw.yukina.sitcon.issue.assistant.entity.Label;
import tw.yukina.sitcon.issue.assistant.entity.RecentNotification;
import tw.yukina.sitcon.issue.assistant.repository.HookFilterRepository;
import tw.yukina.sitcon.issue.assistant.repository.RecentNotificationRepository;
import tw.yukina.sitcon.issue.assistant.util.MessageSupplier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    private HookFilterRepository hookFilterRepository;

    @Autowired
    private TelegramConfig telegramConfig;

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private RecentNotificationRepository recentNotificationRepository;

    public void gitlabHook(JSONObject json){

        Set<GitLabWebhookFilter> gitLabFilters = null;
        if(json.getString("object_kind").equals("issue")){
            gitLabFilters = hookFilterRepository.findAllByObjectKind("issue");
            if(gitLabFilters != null){
                pollingIssueFilter(json, gitLabFilters);
            }

        }
        if(json.getString("object_kind").equals("note")){
            gitLabFilters = hookFilterRepository.findAllByAction("comment");
            if(gitLabFilters != null){
                pollingNoteFilter(json, gitLabFilters);
            }
        }
    }

    private void pollingNoteFilter(JSONObject json, Set<GitLabWebhookFilter> gitLabFilters){
        if(json.getJSONObject("issue") == null)return;

        JSONObject object_attributes = json.getJSONObject("object_attributes");
        for(GitLabWebhookFilter filter : gitLabFilters) {
            if(!filter.getLabels().isEmpty()){
                if(!checkLabel(json.getJSONObject("issue").getJSONArray("labels"), filter))continue;
            }

            //todo

            System.out.println("Filter active!");
            System.out.println(filter.toString());
            activeFilter(filter, json);
        }
    }

    private void pollingIssueFilter(JSONObject json, Set<GitLabWebhookFilter> gitLabFilters){
        JSONObject object_attributes = json.getJSONObject("object_attributes");
        for(GitLabWebhookFilter filter : gitLabFilters) {
            try {
                if(!filterByString(getGLFMethod("getAction"), filter, object_attributes.getString("action")))continue;

                if(!filter.getLabels().isEmpty()){
                    if(!checkLabel(object_attributes.getJSONArray("labels"), filter))continue;
                }

                //todo

                System.out.println("Filter active!");
                System.out.println(filter.toString());
                activeFilter(filter, json);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkLabel(JSONArray labels, GitLabWebhookFilter filter) {
        if(labels == null || labels.isEmpty())return false;

        Set<Label> labelsCopy = new HashSet<>(filter.getLabels());

        for(Object o : labels){
            JSONObject jsonObject = (JSONObject) o;

            labelsCopy.removeIf(label -> filterByString(label.getName(), jsonObject.getString("title")));
        }
        return labelsCopy.isEmpty();
    }

    private void activeFilter(GitLabWebhookFilter filter, JSONObject issue){
        if(filter.getUser() != null){
            sendNotification(filter.getUser().getTelegramUserId(), issue, filter);
        }

        if(filter.getGroup() != null){
            sendNotification(filter.getGroup().getChatId(), issue, filter);
        }
    }

    private void sendNotification(int chatId, JSONObject issue, GitLabWebhookFilter filter){
        String message = null;

        if(issue.getString("object_kind").equals("issue")) message = displayIssueInfo(issue);
        else if(issue.getString("object_kind").equals("note")) message = displayNoteInfo(issue);

        SendMessage.SendMessageBuilder sendMessageBuilder = MessageSupplier.getMarkdownFormatBuilder()
                .text("New notification on GitLab\n" + message)
                .chatId(String.valueOf(chatId));
        telegramConfig.sendMessage(sendMessageBuilder.build());

//        recentNotificationRepository.save(new RecentNotification(LocalDateTime.now(), message, filter));
    }

    private String displayNoteInfo(JSONObject issue){
        JSONObject attributes = issue.getJSONObject("object_attributes");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Action: ");
        stringBuilder.append("comment").append(" by ");
        stringBuilder.append(issue.getJSONObject("user").getString("name"));
        stringBuilder.append("\n------\n\n");

        stringBuilder.append("[#").append(issue.getJSONObject("issue").getInt("iid")).append("](")
                .append(attributes.getString("url")).append(")");
        stringBuilder.append("\n");

        stringBuilder.append("Title: ").append(issue.getJSONObject("issue").getString("title"));
        stringBuilder.append("\n");

        stringBuilder.append("New Comment:\n").append(attributes.getString("description"));
        stringBuilder.append("\n");

        stringBuilder.append("Labels: ");
        if (!issue.getJSONObject("issue").getJSONArray("labels").isEmpty()){
            boolean doFirst = true;
            for(Object o : issue.getJSONObject("issue").getJSONArray("labels")){
                JSONObject jsonObject = (JSONObject) o;
                if(!doFirst) stringBuilder.append(", ");

                stringBuilder.append(jsonObject.getString("title"));
                doFirst = false;
            }
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    private String displayIssueInfo(JSONObject issue){
        JSONObject attributes = issue.getJSONObject("object_attributes");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Action: ");
        stringBuilder.append(attributes.getString("action")).append(" by ");
        stringBuilder.append(issue.getJSONObject("user").getString("name"));
        stringBuilder.append("\n------\n\n");

        stringBuilder.append("[#").append(attributes.getInt("iid")).append("](")
                .append(attributes.getString("url")).append(")");
        stringBuilder.append("\n");

        stringBuilder.append("Title: ").append(attributes.getString("title"));
        stringBuilder.append("\n");

        stringBuilder.append("Description:\n").append(attributes.getString("description"));
        stringBuilder.append("\n\n");

        stringBuilder.append("Labels: ");
        if (!attributes.getJSONArray("labels").isEmpty()){
            boolean doFirst = true;
            for(Object o : attributes.getJSONArray("labels")){
                JSONObject jsonObject = (JSONObject) o;
                if(!doFirst) stringBuilder.append(", ");

                stringBuilder.append(jsonObject.getString("title"));
                doFirst = false;
            }
        }
        stringBuilder.append("\n");

        stringBuilder.append("Assignees: ");
        if (!attributes.getJSONArray("assignee_ids").isEmpty()){
            boolean doFirst = true;
            for(Object o : attributes.getJSONArray("assignee_ids")){
                int assigneeId = (int) o;
                if(!doFirst) stringBuilder.append(", ");

                try {
                    User user = gitLabApi.getUserApi().getUser(assigneeId);
                    stringBuilder.append(user.getName());
                } catch (GitLabApiException e) {
                    e.printStackTrace();
                }
                doFirst = false;
            }
        } else stringBuilder.append("none");
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }


    private Method getGLFMethod(String name) throws NoSuchMethodException {
        return GitLabWebhookFilter.class.getMethod(name);
    }

    private boolean filterByString(Method method, Object object, String jsonString) throws InvocationTargetException, IllegalAccessException {
        Object methodReturn = method.invoke(object);
        if(methodReturn != null){
            String s = (String) methodReturn;

            if(s.equals(""))return true;
            else return s.equals(jsonString);
        } else return true;
    }

    private boolean filterByString(String filterString, String jsonString) {
        if(filterString != null){
            if(filterString.equals(""))return true;
            else return filterString.equals(jsonString);
        } else return true;
    }

    private boolean filterByInteger(Method method, Object object, int jsonInt) throws InvocationTargetException, IllegalAccessException {
        Object methodReturn = method.invoke(object);
        if(methodReturn != null){
            int i = (int) methodReturn;

            if(i == 0)return true;
            else return i == jsonInt;
        }
        return true;
    }

//    private boolean findFilterUseString(JSONObject json, Consumer<String> biConsumer) {
//
//    }
//
//    private boolean findFilterUseInteger(JSONObject json, Consumer<Integer> biConsumer){
//
//    }

    private void findFilter(JSONObject json){

    }

    public List<RecentNotification> getRecentNotificationList() {
        return recentNotificationRepository.findAll();
    }

    public long getFilterCount(){
        return hookFilterRepository.count();
    }

}
