package tw.yukina.sitcon.issue.assistant.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;
import tw.yukina.sitcon.issue.assistant.util.TempPasswordCode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthManager {

    private final Map<String, TempPasswordCode> userAuth = new HashMap<>();

    private final TelegramConfig telegramConfig;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthManager(TelegramConfig telegramConfig, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.telegramConfig = telegramConfig;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean hasTempPassword(String username){
        return userAuth.get(username) != null;
    }

    public String getTempPassword(String username){
        removeExpiredTempPassword();
        return userAuth.get(username) != null ? userAuth.get(username).getTempPasswordCode() : null;
    }

    public void createAndSendTempPassword(String username){
        removeExpiredTempPassword();

        String tempPassword = randomTempPassword();
        String storedEncodePassword = passwordEncoder.encode(tempPassword);

        int telegramUserId = userRepository.findByName(username).getTelegramUserId();
        SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder();
        sendMessageBuilder.chatId(String.valueOf(telegramUserId))
                .text("Your temporary Issue Assistant login code is \"" + tempPassword + "\"");

        userAuth.put(username, new TempPasswordCode(storedEncodePassword));
        telegramConfig.sendMessage(sendMessageBuilder.build());
    }

    private String randomTempPassword(){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 3; i++){
            stringBuilder.append(String.format("%03d", (int) (Math.random() * 1000)));
            if(i < 2)stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private void removeExpiredTempPassword(){
        for (Map.Entry<String, TempPasswordCode> entry : userAuth.entrySet()) {
            if(checkIsExpired(entry.getValue()))userAuth.remove(entry.getKey());
        }
    }

    private boolean checkIsExpired(TempPasswordCode tempPasswordCode){
        return tempPasswordCode.getCreatedDateTime().isBefore(LocalDateTime.now().minusMinutes(5));
    }
}
