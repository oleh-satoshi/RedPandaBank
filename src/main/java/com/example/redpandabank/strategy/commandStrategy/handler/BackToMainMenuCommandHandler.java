package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class BackToMainMenuCommandHandler implements CommandHandler<Update> {
    private final ReplyMainMenuButton replyMainMenuButton;

    public BackToMainMenuCommandHandler(ReplyMainMenuButton replyMainMenuButton) {
        this.replyMainMenuButton = replyMainMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        String response = "Высветил кнопки главного меню";
        ReplyKeyboardMarkup keyboard = replyMainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
    }
}
