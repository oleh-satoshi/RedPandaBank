package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineStartInit implements InlineHandler<Update> {
    final TranslateService translateService;
    final ReplyMainMenuButton mainMenuButton;
    final String HELLO = "hello";

    public InlineStartInit(TranslateService translateService,
                           ReplyMainMenuButton mainMenuButton) {
        this.translateService = translateService;
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String content = translateService.getBySlug(HELLO);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(userId, content, keyboard);
    }
}
