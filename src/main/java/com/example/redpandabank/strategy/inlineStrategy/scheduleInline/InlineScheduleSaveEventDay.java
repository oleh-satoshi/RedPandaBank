package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDay;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleSaveEventDay implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final InlineScheduleAddEventDay inlineScheduleAddEventDay;
    final TranslateService translateService;
    final String SAY_FOR_THE_LESSON = "day-for-the-lesson";

    public InlineScheduleSaveEventDay(LessonService lessonService, ChildService childService,
                                      InlineScheduleAddEventDay inlineScheduleAddEventDay,
                                      TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineScheduleAddEventDay = inlineScheduleAddEventDay;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId).get();
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        child.setState(State.SAVE_EVENT_DAY.getState());
        childService.create(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventDay.getKeyboard();
        response = translateService.getBySlug(SAY_FOR_THE_LESSON);
        return new MessageSenderImpl()
                .sendEditMessageWithInline(userId, messageId, keyboard, response);
    }
}
