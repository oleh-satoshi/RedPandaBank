package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineAddEventByWeekday;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.schedule.InlineAddEventDay;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.List;

@PackagePrivate
@Component
public class InlineAddScheduleEvent implements InlineHandler<Update> {
        final LessonService lessonService;
        final MessageSender messageSender;
        final ChildService childService;
        final InlineAddEventByWeekday inlineAddEventByWeekday;
        final LessonScheduleService lessonScheduleService;
        final InlineAddEventDay inlineAddEventDay;

    public InlineAddScheduleEvent(LessonService lessonService,
                                  MessageSender messageSender,
                                  ChildService childService, InlineAddEventByWeekday inlineAddEventByWeekday,
                                  LessonScheduleService lessonScheduleService,
                                  InlineAddEventDay inlineAddEventDay) {
        this.lessonService = lessonService;
        this.messageSender = messageSender;
        this.childService = childService;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineAddEventDay = inlineAddEventDay;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String title;
        String teacher;
        LocalTime startTime;
        Integer duration;
        String response;
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        String text = update.getCallbackQuery().getData();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Child child = childService.getById(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }

        if (text.contains(Command.ADD_SCHEDULE_EVENT.getName())) {
            response = "Введи название урока:";
            child.setState(State.SAVE_TITLE_EVENT.getState());
            childService.create(child);
            return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
        } else if (text.contains(Command.SAVE_EVENT_TEACHER_NAME.getName())) {
            text = update.getCallbackQuery().getMessage().getText();
            Lesson lesson = lessonService.findLessonByTitle(userId, parseTitle(text));
            response = "Кто преподаёт <i>\"" + lesson.getTitle() + "\"</i>?";
            child.setState(State.SAVE_TEACHER_NAME.getState());
            childService.create(child);
            return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
        } else if (text.contains(Command.SAVE_EVENT_DURATION.getName())) {
            List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
            Lesson lesson = lessons.get(lessons.size() - 1);
            response = "Сколько минут идёт урок <i>\"" + lesson.getTitle() + "\"</i>?";
            child.setState(State.SAVE_DURATION.getState());
            childService.create(child);
            return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
        } else if (text.contains(Command.SAVE_EVENT_DAY.getName())) {
            List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
            Lesson lesson = lessons.get(lessons.size() - 1);
            child.setState(State.SAVE_EVENT_DAY.getState());
            childService.create(child);
            InlineKeyboardMarkup inline = inlineAddEventDay.getInline();
            response = "Выбери день недели в который проходит урок <i>\"" + lesson.getTitle() + "\"</i>:    ";
            return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, inline, response);
        } else if (text.contains(Command.SAVE_EVENT_TIME.getName())) {
            child.setState(State.ADD_EVENT_TIME.getState());
            childService.create(child);
            response = "Напиши просто время числами и раздели часы с минутами двоеточием (:):\n\n" +
                    "вот так 8:00,\nили так 9:45,\nили так 10:25";
            return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
        }
        return null;
    }

    private String parseTitle(String text) {
        String[] array = text.split("\"");
        if (array.length > 1) {
            return array[1];
        }
        return array[0];
    }
}
