package tw.yukina.sitcon.issue.assistant.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.yukina.sitcon.issue.assistant.service.NotificationService;

@RestController
@RequestMapping(path = "webhook")
public class GitLabWebhook {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("issues")
    public ResponseEntity<String> pushIssuesEvents(@RequestBody String str){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(str);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);

        try {
            JSONObject json = new JSONObject(str);

            notificationService.gitlabHook(json);

            String user = json.getJSONObject("user").getString("username");
            System.out.println("name:: " + user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body("ok");
    }
}
