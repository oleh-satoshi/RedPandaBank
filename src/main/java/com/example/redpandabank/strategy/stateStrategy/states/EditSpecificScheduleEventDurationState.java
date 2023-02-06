package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.ReplyScheduleEditSpecificEventDurationStateButton;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class EditSpecificScheduleEventDurationState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String duration;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton;
    final TranslateService translateService;
    final String SET_NEW_DURATION = "set-new-duration";

    public EditSpecificScheduleEventDurationState(ChildService childService,
                                                  LessonService lessonService, ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton, TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.eventDurationStateButton = eventDurationStateButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        duration = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        Long lessonId = parseTitle(child.getState());
        if (checkCommand(duration, child)) {
            Lesson lesson = lessonService.getById(lessonId);
            lesson.setDuration(Integer.parseInt(duration));
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            child.setIsSkip(false);
            childService.create(child);
            String content = translateService.getBySlug(SET_NEW_DURATION)
                    + " <i>\"" + lesson.getTitle() + "\"</i>!";
            InlineKeyboardMarkup keyboard = eventDurationStateButton.getKeyboard();
            String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            return new MessageSenderImpl().sendMessageWithInline(userId, content, keyboard);
        }
        return  goBackToTelegramBot(child, childService, telegramBot, update);
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private Long parseTitle(String name) {
        return Long.parseLong(name.split(Separator.COLON_SEPARATOR)[1]);
    }

}
