package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddExtraDayButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SaveEventDayState implements StateHandler<Update> {
    Long userId;
    String day;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddExtraDayButton inlineScheduleAddExtraDayButton;
    final TranslateService translateService;
    final String ADD_LESSON_START_TIME = "add-lesson-start-time";

    public SaveEventDayState(ChildService childService, LessonService lessonService,
                             LessonScheduleService lessonScheduleService,
                             InlineScheduleAddExtraDayButton inlineScheduleAddExtraDayButton,
                             TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddExtraDayButton = inlineScheduleAddExtraDayButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        day = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String day = update.getCallbackQuery().getData();
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        LessonSchedule lessonSchedule = new LessonSchedule();
        if (day.contains(WeekDay.MONDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.MONDAY.getDay());
        } else if (day.contains(WeekDay.TUESDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.TUESDAY.getDay());
        } else if (day.contains(WeekDay.WEDNESDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.WEDNESDAY.getDay());
        } else if (day.contains(WeekDay.THURSDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.THURSDAY.getDay());
        } else if (day.contains(WeekDay.FRIDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.FRIDAY.getDay());
        } else if (day.contains(WeekDay.SATURDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.SATURDAY.getDay());
        }
        List<LessonSchedule> listLessonSchedule = new ArrayList<>();
        listLessonSchedule.add(lessonSchedule);
        lesson.setLessonSchedules(listLessonSchedule);
        lessonScheduleService.create(lessonSchedule);
        lessonService.create(lesson);
        child.setState(State.NO_STATE.getState());
        childService.create(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddExtraDayButton.getKeyboard();
        String response = translateService.getBySlug(ADD_LESSON_START_TIME);
        return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, keyboard, response);
    }
}
