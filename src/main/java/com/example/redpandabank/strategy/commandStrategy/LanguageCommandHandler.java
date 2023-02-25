package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.keyboard.InlineChooseLanguage;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class LanguageCommandHandler implements CommandHandler<Update> {
    final InlineChooseLanguage inlineChooseLanguage;
    final ChildService childService;
    final TranslateService translateService;
    final String CHOOSE_LANGUAGE = "choose-language";
    final String WE_ARE_MET = "we-are-met";

    public LanguageCommandHandler(InlineChooseLanguage inlineChooseLanguage,
                                  ChildService childService,
                                  TranslateService translateService) {
        this.inlineChooseLanguage = inlineChooseLanguage;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response = translateService.getBySlug(CHOOSE_LANGUAGE);
        InlineKeyboardMarkup keyboard = inlineChooseLanguage.getKeyboard();
        Long userId = UpdateInfo.getUserId(update);
        boolean isInitialize = childService.findById(userId).isPresent();
        if (!isInitialize) {
            Child child = childService.createChild(userId);
            childService.create(child);
            return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
        }
        response = translateService.getBySlug(WE_ARE_MET);
        return new MessageSenderImpl().sendMessage(userId, response);
    }
}
