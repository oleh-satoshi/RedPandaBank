package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDay;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleSaveEventDay implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final InlineScheduleAddEventDay inlineScheduleAddEventDay;

    public InlineScheduleSaveEventDay(LessonService lessonService, ChildService childService,
                                      InlineScheduleAddEventDay inlineScheduleAddEventDay) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineScheduleAddEventDay = inlineScheduleAddEventDay;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        child.setState(State.SAVE_EVENT_DAY.getState());
        childService.create(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventDay.getKeyboard();
        response = "Выбери день недели в который проходит урок <i>\"" + lesson.getTitle() + "\"</i>:    ";
        return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, keyboard, response);
    }
}
