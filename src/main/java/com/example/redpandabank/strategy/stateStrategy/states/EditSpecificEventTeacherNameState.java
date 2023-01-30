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
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class EditSpecificEventTeacherNameState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String teacherName;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;

    public EditSpecificEventTeacherNameState(ChildService childService,
                                             LessonService lessonService,
                                             ReplyMainMenuButton mainMenuButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
    }


    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        teacherName = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(teacherName, child)) {
            Long lessonId = parseId(child.getState());
            Lesson lesson = lessonService.getById(lessonId);
            lesson.setTeacher(teacherName);
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            String response = "Учителя изменили!";
            ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
            String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
