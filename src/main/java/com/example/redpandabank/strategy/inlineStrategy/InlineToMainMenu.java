package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineToMainMenu implements InlineHandler<Update> {
    final ReplyMainMenuButton mainMenuButton;
    final TranslateService translateService;
    final String BACK_MENU = "back-menu";

    public InlineToMainMenu(ReplyMainMenuButton mainMenuButton,
                            TranslateService translateService) {
        this.mainMenuButton = mainMenuButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long chatId = UpdateInfo.getUserId(update);
        String response = translateService.getBySlug(BACK_MENU);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(chatId, response, keyboard);
    }
}
