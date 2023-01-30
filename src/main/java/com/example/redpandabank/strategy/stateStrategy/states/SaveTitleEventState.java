package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleCheckCorrectTitleButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleRepeatAddLessonButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.service.TelegramBot;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@PackagePrivate
@Component
public class SaveTitleEventState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String lessonTitle;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton;
    final InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton;

    public SaveTitleEventState(ChildService childService, LessonService lessonService,
                               InlineScheduleCheckCorrectTitleButton inlineScheduleCheckCorrectTitleButton,
                               InlineScheduleRepeatAddLessonButton inlineScheduleRepeatAddLessonButton) {
        this.lessonService = lessonService;
        this.inlineScheduleCheckCorrectTitleButton = inlineScheduleCheckCorrectTitleButton;
        this.inlineScheduleRepeatAddLessonButton = inlineScheduleRepeatAddLessonButton;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        lessonTitle = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(lessonTitle, child)) {
            if (lessonService.findAllByTitle(lessonTitle, userId)) {
                Lesson lesson = new Lesson();
                lesson.setChildId(userId);
                lesson.setTitle(lessonTitle);
                lesson.setIsDeleted(false);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                ReplyKeyboard keyboard = inlineScheduleCheckCorrectTitleButton.getKeyboard(lesson);
                String response = "Урок \"<i>" + lesson.getTitle()
                        + "\"</i> сохранили!\n\nЕсли ты написал без ошибок то жми кнопку <b>Дальше</b>";
                return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
            } else {
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                ReplyKeyboard keyboard = inlineScheduleRepeatAddLessonButton.getKeyboard();
                return new MessageSenderImpl().sendMessageWithInline(userId,
                        "Такой урок уже сохраняли!", keyboard);
            }
        } else {
          return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }
}
