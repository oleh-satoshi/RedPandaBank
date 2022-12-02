package com.example.redpandabank.strategy.handler;

import com.example.redpandabank.buttons.main.MainMenuButton;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class StartCommandHandler implements CommandHandler {
    private MainMenuButton mainMenuButton;

    public StartCommandHandler(MainMenuButton mainMenuButton) {
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        String response;
        Long userId = update.getMessage().getChatId();

        response = "Привет! Что ты хочешь посмотреть?";
        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(mainMenuButton.getMainMenuButton())
                .build();
        return sendMessage;
    }
}
