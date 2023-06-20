package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDay;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
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
public class InlineScheduleSaveEventDay implements InlineHandler<Update> {
    final ChildService childService;
    final InlineScheduleAddEventDay inlineScheduleAddEventDay;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String SAY_FOR_THE_LESSON = "day-for-the-lesson";

    public InlineScheduleSaveEventDay(ChildService childService,
                                      InlineScheduleAddEventDay inlineScheduleAddEventDay,
                                      TranslateService translateService,
                                      MessageSender messageSender) {
        this.childService = childService;
        this.inlineScheduleAddEventDay = inlineScheduleAddEventDay;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        resetSkipFlagIfSkipped(child);
        setNoStateForUser(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventDay.getKeyboard();
        response = translateService.getBySlug(SAY_FOR_THE_LESSON);
        return messageSender.sendEditMessageWithInline(userId, messageId, keyboard, response);
    }

    private static void resetSkipFlagIfSkipped(Child child) {
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
    }

    private void setNoStateForUser(Child child) {
        child.setState(StateCommands.SAVE_SPECIFIC_EVENT_DAY.getState());
        childService.create(child);
    }
}
