package com.example.redpandabank.strategy.handler;

import com.example.redpandabank.buttons.main.MainMenuButton;
import com.example.redpandabank.service.MessageSender;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class StartCommandHandler implements CommandHandler {
    private final MainMenuButton mainMenuButton;

    public StartCommandHandler(MainMenuButton mainMenuButton) {
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        String response;
        Long userId = update.getMessage().getChatId();

        response = EmojiParser.parseToUnicode("Привет! :blush:") ;
       SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(mainMenuButton.getMainMenuButton())
                .build();
        return sendMessage;
    }
}
