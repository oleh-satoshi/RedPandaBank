package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.InlineStartInitButton;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SetLanguageInlineHandler implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final ReplyMainMenuButton mainMenuButton;
    final InlineStartInitButton inlineStartInitButton;
    final String ADVENTURE = "adventure";

    public SetLanguageInlineHandler(ChildService childService,
                                    TranslateService translateService,
                                    ReplyMainMenuButton mainMenuButton,
                                    InlineStartInitButton inlineStartInitButton) {
        this.childService = childService;
        this.translateService = translateService;
        this.mainMenuButton = mainMenuButton;
        this.inlineStartInitButton = inlineStartInitButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        String data = parseLanguage(UpdateInfo.getData(update));
        Child child = childService.getById(userId).get();
        child.setLanguage(data);
        childService.create(child);
        response = translateService.getBySlug(ADVENTURE);
        InlineKeyboardMarkup keyboard = inlineStartInitButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, keyboard, response);

    }

    private String parseLanguage(String text) {
        return text.split(Separator.COLON_SEPARATOR)[1];
    }
}
