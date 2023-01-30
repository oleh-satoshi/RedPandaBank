package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
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
public class EditSpecificEventStartTimeState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String lessonTitle;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;

    public EditSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                           LessonScheduleService lessonScheduleService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        lessonTitle = UpdateInfo.hasReply(update) ? UpdateInfo.getText(update) : parseEventTitle(
                UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        if (checkCommand(lessonTitle, child)) {
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
            String response = "Можешь ввести новое время для урока <i>\"" + lesson.getTitle() + "\"</i>";
            return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

        @Override
        public boolean checkCommand (String command, Child child){
            return CommandCheckable.super.checkCommand(command, child);
        }

        private String parseEventTitle (String name){
            return name.split(Separator.QUOTE_SEPARATOR)[1];
        }

    private LocalTime parseTimeWithTitle(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }
}
