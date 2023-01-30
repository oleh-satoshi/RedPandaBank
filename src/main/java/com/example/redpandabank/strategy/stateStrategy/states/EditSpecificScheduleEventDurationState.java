package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.ReplyScheduleEditSpecificEventDurationStateButton;
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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class EditSpecificScheduleEventDurationState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String duration;
    String title;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton;

    public EditSpecificScheduleEventDurationState(ChildService childService,
                                                  LessonService lessonService, ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.eventDurationStateButton = eventDurationStateButton;
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
            String content = "Новая длительность для урока <i>\"" + lesson.getTitle() + "\"</i> установлена!";
            InlineKeyboardMarkup keyboard = eventDurationStateButton.getKeyboard();
            String infoLesson = lessonService.getInfoLessonbyIdAndSendByUrl(lesson.getLessonId());
            new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
            return new MessageSenderImpl().sendMessageWithInline(userId, content, keyboard);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private Long parseTitle(String name) {
        return Long.parseLong(name.split(Separator.COLON_SEPARATOR)[1]);
    }

}
