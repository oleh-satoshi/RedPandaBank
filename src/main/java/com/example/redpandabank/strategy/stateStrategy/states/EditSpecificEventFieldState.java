package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class EditSpecificEventFieldState implements StateHandler<Update> {
    Long userId;
    String duration;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;
    final TranslateService translateService;
    final String LESSON_WAS_SAVE = "lesson-was-save";

    public EditSpecificEventFieldState(ChildService childService, LessonService lessonService,
                                       ReplyMainMenuButton mainMenuButton,
                                       TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        duration = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        Long lessonId = parseId(child.getState());
        Lesson lesson = lessonService.getById(lessonId);
        String newTitle = UpdateInfo.getText(update);
        lesson.setTitle(newTitle);
        lessonService.create(lesson);
        child.setState(State.NO_STATE.getState());
        childService.create(child);
        String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
        String response = translateService.getBySlug(LESSON_WAS_SAVE);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }

}
