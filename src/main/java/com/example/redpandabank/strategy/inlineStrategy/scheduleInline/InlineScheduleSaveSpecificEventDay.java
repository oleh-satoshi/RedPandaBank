package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddLessonStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class InlineScheduleSaveSpecificEventDay implements InlineHandler<Update> {
    Long userId;
    String day;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddLessonStartTimeButton inlineScheduleAddLessonStartTimeButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ADD_LESSON_START_TIME = "add-lesson-start-time";

    public InlineScheduleSaveSpecificEventDay
            (ChildService childService, LessonService lessonService,
                             LessonScheduleService lessonScheduleService,
                             InlineScheduleAddLessonStartTimeButton inlineScheduleAddLessonStartTimeButton,
                             TranslateService translateService, MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddLessonStartTimeButton = inlineScheduleAddLessonStartTimeButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        day = UpdateInfo.getData(update);
        Child child = childService.findByUserId(userId);
        Integer messageId = UpdateInfo.getMessageId(update);
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        setUpLessonSchedule(lesson, userId);
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddLessonStartTimeButton.getKeyboard();
        String response = translateService.getBySlug(ADD_LESSON_START_TIME);
        return messageSender.sendEditMessageWithInline(userId, messageId, keyboard, response);
    }

    private void setUpLessonSchedule(Lesson lesson, Long userId) {
        LessonSchedule lessonSchedule = new LessonSchedule();
        setDayForLessonSchedule(lessonSchedule);
        List<LessonSchedule> listLessonSchedule = new ArrayList<>();
        listLessonSchedule.add(lessonSchedule);
        lesson.setLessonSchedules(listLessonSchedule);
        lessonSchedule.setUserId(userId);
        lessonScheduleService.create(lessonSchedule);
        lessonService.create(lesson);
    }

    private void setDayForLessonSchedule(LessonSchedule lessonSchedule) {
        if (day.contains(WeekDay.MONDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.MONDAY.getDay());
        } else if (day.contains(WeekDay.TUESDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.TUESDAY.getDay());
        } else if (day.contains(WeekDay.WEDNESDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.WEDNESDAY.getDay());
        } else if (day.contains(WeekDay.THURSDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.THURSDAY.getDay());
        } else if (day.contains(WeekDay.FRIDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.FRIDAY.getDay());
        } else if (day.contains(WeekDay.SATURDAY.getDay())) {
            lessonSchedule.setDay(WeekDay.SATURDAY.getDay());
        }
    }
}
