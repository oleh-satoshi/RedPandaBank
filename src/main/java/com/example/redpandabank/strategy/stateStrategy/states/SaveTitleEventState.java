package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleCheckCorrectTitleButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleRepeatAddLessonButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SaveTitleEventState implements StateHandler<Update> {
    Long userId;
    String lessonTitle;
    final ChildService childService;
    final LessonService lessonService;
    final MessageSender messageSender;
    final InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton;
    final InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton;
    final TranslateService translateService;
    final String LESSON = "lesson";
    final String LESSON_SAVED_CHECK = "smth-saved-check";
    final String NEXT_BUTTON = "next";
    final String LESSON_ALREADY_SAVED = "lesson-already-saved";

    public SaveTitleEventState(ChildService childService, LessonService lessonService,
                               MessageSender messageSender,
                               InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton,
                               InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton,
                               TranslateService translateService) {
        this.lessonService = lessonService;
        this.messageSender = messageSender;
        this.inlineScheduleCheckCorrectTitleButton = inlineScheduleCheckCorrectTitleButton;
        this.inlineScheduleRepeatAddLessonButton = inlineScheduleRepeatAddLessonButton;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        userId = UpdateInfo.getUserId(update);
        lessonTitle = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (lessonService.checkAllByTitle(lessonTitle, userId)) {
            Lesson lesson = createLesson(update);
            setNoStateForUser(child);
            ReplyKeyboard keyboard = inlineScheduleCheckCorrectTitleButton.getKeyboard(lesson);
            response = getResponseBySlug(lesson);
            return messageSender.sendMessageWithInline(userId, response, keyboard);
        }
        setNoStateForUser(child);
        ReplyKeyboard keyboard = inlineScheduleRepeatAddLessonButton.getKeyboard();
        response = translateService.getBySlug(LESSON_ALREADY_SAVED);
        return messageSender.sendMessageWithInline(userId, response, keyboard);
    }

    private void setNoStateForUser(Child child) {
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
    }

    private String getResponseBySlug(Lesson lesson) {
        return translateService.getBySlug(LESSON)
                + "\"<i>" + lesson.getTitle() + "\"</i> "
                + translateService.getBySlug(LESSON_SAVED_CHECK)
                + "<b>" + translateService.getBySlug(NEXT_BUTTON) + "</b>";
    }

    private Lesson createLesson(Update update) {
        userId = UpdateInfo.getUserId(update);
        lessonTitle = UpdateInfo.getText(update);
        Lesson lesson = new Lesson();
        lesson.setUserId(userId);
        lesson.setTitle(lessonTitle);
        lesson.setIsDeleted(false);
        lessonService.create(lesson);
        return lesson;
    }
}
