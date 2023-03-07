package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleShowAllDaysButton;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
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
public class InlineScheduleChooseEventByDay implements InlineHandler<Update> {
    final InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton;
    final TranslateService translateService;
    final String SHOW_LESSON_ON_SPECIFIC_DAY = "show-lesson-on-specific-day";

    public InlineScheduleChooseEventByDay(InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton,
                                          TranslateService translateService) {
        this.inlineScheduleShowAllDaysButton = inlineScheduleShowAllDaysButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String content = translateService.getBySlug(SHOW_LESSON_ON_SPECIFIC_DAY);
        InlineKeyboardMarkup keyboard = inlineScheduleShowAllDaysButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, content);
    }
}
