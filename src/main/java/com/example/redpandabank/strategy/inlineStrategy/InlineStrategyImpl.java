package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.main.BackToMainMenuButton;
import com.example.redpandabank.keyboard.schedule.InlineAddEventByWeekday;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.schedule.InlineEditScheduleEventFieldButton;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.inlineStrategy.ScheduleInline.*;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@PackagePrivate
@Component
public class InlineStrategyImpl implements InlineStrategy {
    Map<String, InlineHandler> strategyMap;
    final BackToMainMenuButton backToMainMenuButton;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final InlineAddEventByWeekday inlineAddEventByWeekday;
    final InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton;
    final InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder;

    public InlineStrategyImpl(BackToMainMenuButton backToMainMenuButton,
                              LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              MessageSender messageSender,
                              InlineAddEventByWeekday inlineAddEventByWeekday,
                              InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton,
                              InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder) {
        this.lessonService = lessonService;
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
        this.inlineEditScheduleEventFieldButton = inlineEditScheduleEventFieldButton;
        this.inlineKeyboardMarkupBuilder = inlineKeyboardMarkupBuilder;
        strategyMap = new HashMap<>();
        strategyMap.put("/scheduleMonday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleTuesday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleWednesday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleThursday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleFriday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSaturday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSunday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put(WeekDay.MONDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.TUESDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.THURSDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.FRIDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.SATURDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.SUNDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(Command.DELETE_EVENT.getName(), new InlineDeleteEventButton(lessonService));
        strategyMap.put(Command.DELETE_EVENT_BY_ID.getName(), new InlineDeleteEvent(lessonScheduleService, lessonService, this.messageSender));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_FIELD.getName(), new InlineEditScheduleEventField(lessonService, this.inlineEditScheduleEventFieldButton, this.inlineKeyboardMarkupBuilder));
        strategyMap.put(Command.RECOVER_EVENT_BY_ID.getName(), new InlineRecoverEvent(lessonService));
    }

    @Override
    public InlineHandler get(String command) {
        command = checkDeleteEventById(command);
        command = checkEditEventField(command);
        command = checkRecoverEvent(command);
        InlineHandler inlineHandler = strategyMap.get(command);
        return inlineHandler;
    }

    private String checkDeleteEventById(String command) {
        String name = Command.DELETE_EVENT_BY_ID.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEditEventField(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_FIELD.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkRecoverEvent(String command) {
        String name = Command.RECOVER_EVENT_BY_ID.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }
}
