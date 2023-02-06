package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleCheckCorrectTitleButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleRepeatAddLessonButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class SaveTitleEventState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String lessonTitle;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton;
    final InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton;
    final TranslateService translateService;
    final String TEACHER = "teacher";
    final String TEACHER_SAVED_CHECK = "teacher-saved-check";
    final String NEXT_BUTTON = "next";
    final String LESSON_ALREADY_SAVED = "lesson-already-saved";

    public SaveTitleEventState(ChildService childService, LessonService lessonService,
                               InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton,
                               InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton,
                               TranslateService translateService) {
        this.lessonService = lessonService;
        this.inlineScheduleCheckCorrectTitleButton = inlineScheduleCheckCorrectTitleButton;
        this.inlineScheduleRepeatAddLessonButton = inlineScheduleRepeatAddLessonButton;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        String response;
        userId = UpdateInfo.getUserId(update);
        lessonTitle = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);

        if (checkCommand(lessonTitle, child)) {
            if (lessonService.checkAllByTitle(lessonTitle, userId)) {
                Lesson lesson = new Lesson();
                lesson.setChildId(userId);
                lesson.setTitle(lessonTitle);
                lesson.setIsDeleted(false);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                ReplyKeyboard keyboard = inlineScheduleCheckCorrectTitleButton.getKeyboard(lesson);
                response = translateService.getBySlug(TEACHER)
                        + "\"<i>" + lesson.getTitle() + "\"</i> "
                        + translateService.getBySlug(TEACHER_SAVED_CHECK)
                        + "<b>" + translateService.getBySlug(NEXT_BUTTON) + "</b>";
                return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
            } else {
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                ReplyKeyboard keyboard = inlineScheduleRepeatAddLessonButton.getKeyboard();
                response = translateService.getBySlug(LESSON_ALREADY_SAVED);
                return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
            }
        }
        return  goBackToTelegramBot(child, childService, telegramBot, update);
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }
}
