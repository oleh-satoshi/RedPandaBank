package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class EditSpecificEventStartTimeState implements StateHandler<Update> {
    Long userId;
    String lessonTitle;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final TranslateService translateService;
    final String ENTER_NEW_TIME_FOR_LESSON = "enter-new-time-for-lesson";

    public EditSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                           LessonScheduleService lessonScheduleService,
                                           TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        lessonTitle = UpdateInfo.hasReply(update) ? UpdateInfo.getText(update) :
                parseEventTitle(UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        LocalTime localTime = parseTimeWithTitle( UpdateInfo.getData(update));
        Lesson lesson = lessonService.findLessonByTitle(userId, lessonTitle);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        LessonSchedule specificLessonSchedule = lessonSchedules.stream()
                .filter(lessonSchedule -> lessonSchedule.getLessonStartTime().equals(localTime))
                .findFirst()
                .get();
        specificLessonSchedule.setLessonStartTime(LocalTime.MIN);
        lessonScheduleService.create(specificLessonSchedule);
        child.setIsSkip(false);
        child.setState(State.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState()
                + Separator.COLON_SEPARATOR + lesson.getTitle());
        childService.create(child);
        String response = translateService.getBySlug(ENTER_NEW_TIME_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i>";
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }

    private String parseEventTitle (String name){
        return name.split(Separator.QUOTE_SEPARATOR)[1];
    }

    private LocalTime parseTimeWithTitle(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }
}
