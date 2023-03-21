package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class BackToMainMenuCommandHandler implements CommandHandler<Update> {
    final ReplyMainMenuButton replyMainMenuButton;
    final TranslateService translateService;
    final String MENU = "menu";

    public BackToMainMenuCommandHandler(ReplyMainMenuButton replyMainMenuButton,
                                        TranslateService translateService) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.translateService = translateService;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        String response = translateService.getBySlug(MENU);
        ReplyKeyboardMarkup keyboard = replyMainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
    }
}
