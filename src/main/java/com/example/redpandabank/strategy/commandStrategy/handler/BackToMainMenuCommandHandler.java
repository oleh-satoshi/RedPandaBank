package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.keyboard.main.MainMenuButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BackToMainMenuCommandHandler implements CommandHandler<Update> {
    private final MainMenuButton mainMenuButton;

    public BackToMainMenuCommandHandler(MainMenuButton mainMenuButton) {
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        String response = "Высветил кнопки главного меню";

        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(mainMenuButton.getMainMenuButton())
                .build();
        return sendMessage;
    }
}
