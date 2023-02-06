package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class EditSpecificEventTeacherNameState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String teacherName;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;
    final TranslateService translateService;
    final String TEACHER_CHANGED = "teacher-changed";

    public EditSpecificEventTeacherNameState(ChildService childService,
                                             LessonService lessonService,
                                             ReplyMainMenuButton mainMenuButton,
                                             TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
        this.translateService = translateService;
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
            String response = translateService.getBySlug(TEACHER_CHANGED);
            ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
            String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
        }
        return  goBackToTelegramBot(child, childService, telegramBot, update);
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
