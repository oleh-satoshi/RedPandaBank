package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineShowMainMenu implements InlineHandler<Update> {
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final TranslateService translateService;
    final String WHAT_INTERSTED_IN = "what-interested-in";

    public InlineShowMainMenu(InlineScheduleMenuButton inlineScheduleMenuButton,
                              TranslateService translateService) {
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String content = translateService.getBySlug(WHAT_INTERSTED_IN);
        InlineKeyboardMarkup keyboard = inlineScheduleMenuButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, keyboard, content);
    }
}
