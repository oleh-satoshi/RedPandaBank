package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeAndDayButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SaveEventTimeState implements StateHandler<Update> {
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String LESSON_ADDED_TO_SCHEDULE = "lesson-added-to-schedule";

    public SaveEventTimeState(ChildService childService,
                              LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton,
                              TranslateService translateService,
                              MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddEventTimeAndDayButton = inlineScheduleAddEventTimeAndDayButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String time = UpdateInfo.getText(update);
        setStartTimeForSpecificLesson(userId, time);
        setNoStateForUser(userId);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventTimeAndDayButton.getKeyboard();
        String response = translateService.getBySlug(LESSON_ADDED_TO_SCHEDULE);
        return messageSender.sendMessageWithInline(userId, response, keyboard);
    }

    private void setStartTimeForSpecificLesson(Long userId, String time) {
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        int size = lesson.getLessonSchedules().size() - 1;
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().get(size);
        lessonSchedule.setLessonStartTime(parseTime(time));
        List<LessonSchedule> lessonSchedules = new ArrayList<>();
        lessonSchedules.add(lessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonService.create(lesson);
    }

    private void setNoStateForUser(Long userId) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
    }

    private LocalTime parseTime(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
