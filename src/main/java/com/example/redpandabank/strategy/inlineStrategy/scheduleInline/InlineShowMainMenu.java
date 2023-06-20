package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
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
    final MessageSender messageSender;
    final String WHAT_INTERSTED_IN = "what-interested-in";

    public InlineShowMainMenu(InlineScheduleMenuButton inlineScheduleMenuButton,
                              TranslateService translateService,
                              MessageSender messageSender) {
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String content = translateService.getBySlug(WHAT_INTERSTED_IN);
        InlineKeyboardMarkup keyboard = inlineScheduleMenuButton.getKeyboard();
        return messageSender.sendMessageWithInline(userId, content, keyboard);
    }
}
