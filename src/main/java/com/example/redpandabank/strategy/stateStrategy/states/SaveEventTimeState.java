package com.example.redpandabank.strategy.stateStrategy.states;

import java.util.ArrayList;
import java.util.List;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeButton;
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
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SaveEventTimeState implements StateHandler<Update> {
    Long userId;
    String time;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton;
    final TranslateService translateService;
    final String LESSON_ADDED_TO_SCHEDULE = "lesson-added-to-schedule";

    public SaveEventTimeState(ChildService childService,
                              LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton,
                              TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddEventTimeButton = inlineScheduleAddEventTimeButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        time = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        String time = update.getMessage().getText();
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        int size = lesson.getLessonSchedules().size() - 1;
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().get(size);
        lessonSchedule.setLessonStartTime(parseTime(time));
        List<LessonSchedule> lessonSchedules = new ArrayList<>();
        lessonSchedules.add(lessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonService.create(lesson);
        child.setState(State.NO_STATE.getState());
        childService.create(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventTimeButton.getKeyboard();
        String response = translateService.getBySlug(LESSON_ADDED_TO_SCHEDULE);
        return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
    }

    private LocalTime parseTime(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
