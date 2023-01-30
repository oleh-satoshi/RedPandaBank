package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SaveEventTimeState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String time;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton;

    public SaveEventTimeState(ChildService childService,
                              LessonService lessonService,
                              LessonScheduleService lessonScheduleService, InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddEventTimeButton = inlineScheduleAddEventTimeButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        time = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(time, child)) {
            String time = update.getMessage().getText();
            List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
            Lesson lesson = lessons.get(lessons.size() - 1);
            int size = lesson.getLessonSchedules().size() - 1;
            LessonSchedule lessonSchedule = lesson.getLessonSchedules().get(size);
            lessonSchedule.setLessonStartTime(parseTime(time));
            List<LessonSchedule> lessonSchedules = new ArrayList<>();
            lessonSchedules.add(lessonSchedule);
            lesson.setLessonSchedules(lessonSchedules);
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            InlineKeyboardMarkup keyboard = inlineScheduleAddEventTimeButton.getKeyboard();
            String response = "Готово! Урок добавлен в твое расписание!";
            return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private LocalTime parseTime(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
