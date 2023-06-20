package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class EditSpecificEventStartTimeState implements StateHandler<Update> {
    Long userId;
    String lessonTitle;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_NEW_TIME_FOR_LESSON = "enter-new-time-for-lesson";

    public EditSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                           LessonScheduleService lessonScheduleService,
                                           TranslateService translateService,
                                           MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        lessonTitle = parseEventTitle(UpdateInfo.getData(update));
        LocalTime localTime = parseTimeWithTitle(UpdateInfo.getData(update));
        Lesson lesson = lessonService.findLessonByTitle(userId, lessonTitle);
        LessonSchedule specificLessonSchedule = getLessonSchedule(localTime, lesson);
        setMinStartTime(specificLessonSchedule);
        setEditTime2State(lesson, userId);
        String response = getResponse(lesson);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(ENTER_NEW_TIME_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i>";
    }

    private void setEditTime2State(Lesson lesson, Long userId) {
        Child child = childService.findByUserId(userId);
        child.setIsSkip(false);
        child.setState(StateCommands.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState()
                + Separator.COLON_SEPARATOR + lesson.getTitle());
        childService.create(child);
    }

    private void setMinStartTime(LessonSchedule specificLessonSchedule) {
        specificLessonSchedule.setLessonStartTime(LocalTime.MIN);
        lessonScheduleService.create(specificLessonSchedule);
    }

    private static LessonSchedule getLessonSchedule(LocalTime localTime, Lesson lesson) {
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        LessonSchedule specificLessonSchedule = lessonSchedules.stream()
                .filter(lessonSchedule -> lessonSchedule.getLessonStartTime().equals(localTime))
                .findFirst()
                .get();
        return specificLessonSchedule;
    }

    private String parseEventTitle(String name) {
        return name.split(Separator.QUOTE_SEPARATOR)[1];
    }

    private LocalTime parseTimeWithTitle(String text) {
        String[] response = text.split(Separator.QUOTE_SEPARATOR)[2]
                .split(Separator.COLON_SEPARATOR);
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
