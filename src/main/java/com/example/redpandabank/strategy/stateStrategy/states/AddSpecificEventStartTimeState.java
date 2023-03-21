package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddSpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AddSpecificEventStartTimeState implements StateHandler<Update> {
    Long userId;
    LocalTime localTime;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddSpecificEventStartTimeButton startTimeButton;
    final TranslateService translateService;
    final String TIME_ALREADY_ADD_FOR_LESSON = "time-already-add-for-lesson";
    final String WHAT_DAY_OF_WEEK = "what-day-of-week";

    public AddSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                          LessonScheduleService lessonScheduleService,
                                          InlineScheduleAddSpecificEventStartTimeButton startTimeButton,
                                          TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.startTimeButton = startTimeButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        localTime = parseTime(UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        String title = parseTitleFromState(child.getState());
        LocalTime localTime = parseTime(update.getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        LessonSchedule lessonSchedule = new LessonSchedule();
        lessonSchedule.setLessonStartTime(localTime);
        lessonSchedules.add(lessonSchedule);
        lessonScheduleService.create(lessonSchedule);
        lessonService.create(lesson);
        child.setState(State.NO_STATE.getState());
        child.setIsSkip(false);
        childService.create(child);
        String response = translateService.getBySlug(TIME_ALREADY_ADD_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i>,"
                + translateService.getBySlug(WHAT_DAY_OF_WEEK);
        InlineKeyboardMarkup keyboard = startTimeButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
    }

    private LocalTime parseTime(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }

    private String parseTitleFromState(String name) {
        return name.split(Separator.COLON_SEPARATOR)[1];
    }

}
