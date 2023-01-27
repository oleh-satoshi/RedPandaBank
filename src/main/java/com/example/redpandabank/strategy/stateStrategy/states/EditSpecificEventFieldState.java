package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.service.TelegramBot;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class EditSpecificEventFieldState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String duration;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;

    public EditSpecificEventFieldState(ChildService childService, LessonService lessonService,
                                       ReplyMainMenuButton mainMenuButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        duration = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(duration, child)) {
            String oldTitle = parseTitleFromText(child.getState());
            Lesson lesson = lessonService.findLessonByTitle(userId, oldTitle);
            String newTitle = update.getMessage().getText();
            lesson.setTitle(newTitle);
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            String infoLesson = lessonService.getInfoLessonbyIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            String response = "Название урока успешно сохранили! Пойду поем!";
            ReplyKeyboardMarkup menuButton = mainMenuButton.getMainMenuButton();
            return new MessageSenderImpl().sendMessageWithReply(userId, response, menuButton);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private String parseTitleFromText(String name) {
        return name.split(LessonService.COLON_SEPARATOR)[1];
    }


}
