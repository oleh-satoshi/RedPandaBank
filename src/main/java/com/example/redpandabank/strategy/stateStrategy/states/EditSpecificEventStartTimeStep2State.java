package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.LocalTime;

public class EditSpecificEventStartTimeStep2State implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String lessonTitle;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ReplyMainMenuButton mainMenuButton;

    public EditSpecificEventStartTimeStep2State(ChildService childService, LessonService lessonService,
                                                LessonScheduleService lessonScheduleService,
                                                ReplyMainMenuButton mainMenuButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.mainMenuButton = mainMenuButton;
    }


    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        lessonTitle = UpdateInfo.hasReply(update) ? UpdateInfo.getText(update) : parseEventTitle(
                UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        if (checkCommand(lessonTitle, child)) {
            String title = parseTitleFromState(child.getState());
            LocalTime localTime = parseTime(update.getMessage().getText());
            Lesson lesson = lessonService.findLessonByTitle(userId, title);
            LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                    .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(LocalTime.MIN))
                    .findFirst()
                    .get();
            lessonSchedule.setLessonStartTime(localTime);
            lessonScheduleService.create(lessonSchedule);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            String response = "Время начала урока изменили!";
            ReplyKeyboardMarkup menuButton = mainMenuButton.getMainMenuButton();
            String infoLesson = lessonService.getInfoLessonbyIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            return new MessageSenderImpl().sendMessageWithReply(userId, response, menuButton);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand (String command, Child child){
        return CommandCheckable.super.checkCommand(command, child);
    }

    private String parseEventTitle (String name){
        return name.split(LessonService.QUOTE_SEPARATOR)[1];
    }

    private String parseTitleFromState(String name) {
        return name.split(LessonService.COLON_SEPARATOR)[1];
    }

    private LocalTime parseTime(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
