package tw.yukina.sitcon.issue.assistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tw.yukina.sitcon.issue.assistant.manager.telegram.TelegramManager;

@Component
public class TelegramConfig extends TelegramLongPollingBot {

    @Value("${telegram.token}")
    private String telegramToken;

    @Value("${telegram.username}")
    private String telegramUsername;

    private final TelegramManager telegramManager;

    public TelegramConfig(TelegramManager telegramManager){
        this.telegramManager = telegramManager;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            telegramManager.messageInput(update);
        }
    }

    public void sendMessage(SendMessage message){
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }
}
