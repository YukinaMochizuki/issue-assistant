package tw.yukina.sitcon.issue.assistant.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "webhook")
public class GitLabWebhook {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("issues")
    public ResponseEntity<String> pushIssuesEvents(@RequestBody String str){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(str);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);

        return ResponseEntity.ok().body("ok");
    }
}
