package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.inlineStrategy.mainMenu.InlineToMainMenu;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineAddScheduleEvent;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.EditSchedule;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.ChooseEventByDay;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.*;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@PackagePrivate
@Component
public class InlineStrategyImpl implements InlineStrategy {
    Map<String, InlineHandler> strategyMap;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final InlineAddEventByWeekday inlineAddEventByWeekday;
    final InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton;
    final InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder;
    final InlineShowAllDays inlineShowAllDays;
    final MenuButton menuButton;
    final InlineEditScheduleMenuButton inlineEditScheduleMenuButton;
    final ChildService childService;
    final InlineAddEventDay inlineAddEventDay;
    final InlineEditSpecificEventStartTimeChooseOperationButton chooseOperationButton;
    final InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton;
    final InlineAddExtraDaySpecificStartTimeButton inlineAddExtraDaySpecificStartTimeButton;
    final ReplyMainMenuButton mainMenuButton;
    final InlineDeleteSpecificEventStartTime2Button specificEventStartTime2Button;


    public InlineStrategyImpl(LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              MessageSender messageSender,
                              InlineAddEventByWeekday inlineAddEventByWeekday,
                              InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton,
                              InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder,
                              InlineShowAllDays inlineShowAllDays, MenuButton menuButton,
                              InlineEditScheduleMenuButton inlineEditScheduleMenuButton,
                              ChildService childService, InlineAddEventDay inlineAddEventDay,
                              InlineEditSpecificEventStartTimeChooseOperationButton chooseOperationButton,
                              InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton,
                              InlineAddExtraDaySpecificStartTimeButton inlineAddExtraDaySpecificStartTimeButton,
                              ReplyMainMenuButton mainMenuButton,
                              InlineDeleteSpecificEventStartTime2Button specificEventStartTime2Button) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
        this.inlineEditScheduleEventFieldButton = inlineEditScheduleEventFieldButton;
        this.inlineKeyboardMarkupBuilder = inlineKeyboardMarkupBuilder;
        this.inlineShowAllDays = inlineShowAllDays;
        this.menuButton = menuButton;
        this.inlineEditScheduleMenuButton = inlineEditScheduleMenuButton;
        this.childService = childService;
        this.inlineAddEventDay = inlineAddEventDay;
        this.chooseOperationButton = chooseOperationButton;
        this.inlineAddDaySpecificEventStartTimeButton = inlineAddDaySpecificEventStartTimeButton;
        this.inlineAddExtraDaySpecificStartTimeButton = inlineAddExtraDaySpecificStartTimeButton;
        this.mainMenuButton = mainMenuButton;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
        strategyMap = new HashMap<>();
        strategyMap.put(Command.TO_MAIN_MENU.getName(), new InlineToMainMenu(this.mainMenuButton));
        strategyMap.put("/scheduleMonday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleTuesday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleWednesday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleThursday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleFriday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSaturday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSunday", new InlineScheduleWeekdayButton(lessonService, this.lessonScheduleService));
        strategyMap.put(WeekDay.MONDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.TUESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.THURSDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.FRIDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SATURDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SUNDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(Command.DELETE_EVENT.getName(), new InlineDeleteEvent(lessonService));
        strategyMap.put(Command.DELETE_EVENT_BY_ID.getName(), new InlineDeleteEventStep2(lessonScheduleService, lessonService, this.messageSender));
        strategyMap.put(Command.RECOVER_EVENT_BY_ID.getName(), new InlineRecoverEvent(lessonService));
        strategyMap.put(Command.CHOOSE_EVENT_BY_DAY.getName(), new ChooseEventByDay(this.lessonService, this.inlineShowAllDays));
        strategyMap.put(Command.EDIT_SCHEDULE.getName(), new EditSchedule(inlineEditScheduleMenuButton));
        strategyMap.put(Command.SCHEDULE.getName(), new InlineShowMainMenu(this.menuButton));
        strategyMap.put(Command.SAVE_EVENT_NAME.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.SAVE_EVENT_TIME.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.SAVE_EVENT_TEACHER_NAME.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.ADD_SCHEDULE_EVENT.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.SAVE_EVENT_DURATION.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.SAVE_EVENT_MONDAY.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TITLE.getName(), new InlineChangeEventTitle(this.lessonService, this.childService));
        strategyMap.put(Command.EDIT_EVENT_TEACHER_NAME.getName(), new InlineChangeTeacher(lessonService, childService));
        strategyMap.put(Command.EDIT_EVENT_DURATION.getName(), new InlineChangeDuration(lessonService, childService));
        strategyMap.put(Command.SAVE_EVENT_DAY.getName(), new InlineAddScheduleEvent(this.lessonService, this.messageSender, this.childService, this.inlineAddEventByWeekday, this.lessonScheduleService, this.inlineAddEventDay));
        strategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(), new InlineEditScheduleEvent(lessonService));
        strategyMap.put(Command.EDIT_SPECIFIC_EXISTING_EVENT.getName(), new InlineEditSpecificExistingEvent(lessonService, lessonScheduleService, inlineEditScheduleEventFieldButton, inlineKeyboardMarkupBuilder));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_FIELD.getName(), new InlineEditScheduleEventField(lessonService, childService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TEACHER.getName(), new InlineEditScheduleEvenTeacherName(lessonService, childService));
        strategyMap.put(Command.SHOW_SPECIFIC_EVENT_START_TIME.getName(), new InlineEditScheduleEventLessonStartTime(lessonService, childService, chooseOperationButton));
        strategyMap.put(Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName(), new InlineEditSpecificEventStartTimeChooseOperation(chooseOperationButton, lessonService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_START_TIME.getName(), new InlineEditScheduleEventLessonStartTime(lessonService, childService, chooseOperationButton));
        strategyMap.put(Command.ADD_SPECIFIC_EVENT_START_TIME.getName(), new InlineAddTimeToLessonSchedule(lessonService, childService));
        strategyMap.put(Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineAddDaySpecificEventStartTime(lessonScheduleService, lessonService, childService, inlineAddDaySpecificEventStartTimeButton));
        strategyMap.put(Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineAddExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineAddExtraDaySpecificStartTimeButton));
        strategyMap.put(Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineSetExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineAddDaySpecificEventStartTimeButton));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME.getName(), new InlineDeleteSpecificEventStartTime(lessonService));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName(), new InlineDeleteSpecificEventStartTime2(lessonService, lessonScheduleService, specificEventStartTime2Button));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_DURATION.getName(), new InlineEditSpecificEventDuration(lessonService, childService));
    }

    @Override
    public InlineHandler get(String command) {
        command = checkDeleteEventById(command);
        command = checkEditEventField(command);
        command = checkRecoverEvent(command);
        command = checkTeacherName(command);
        command = checkDuration(command);
        command = checkEventDay(command);
        command = checkEditSpecificExistingEvent(command);
        command = checkEditSpecificEventField(command);
        command = checkEditSpecificEventTeacherName(command);
        command = checkEditSpecificEventLessonStartTime(command);
        command = checkSpecificEventChooseOperation(command);
        command = checkEditScheduleEventStartTime(command);
        command = checkAddTimeToLessonSchedule(command);
        command = checkAddDaySpecificStartTime(command);
        command = checkSetExtraDaySpecificStartTime(command);
        command = checkDeleteSpecificEventStartTime2(command);
        command = checkInlineEditSpecificEventDuration(command);
        InlineHandler inlineHandler = strategyMap.get(command);
        if (inlineHandler == null) {
            inlineHandler = new InlinePlug();
        }
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
        String name = Command.EDIT_SPECIFIC_EVENT_FIELD.getName();
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

    private String checkTeacherName(String command) {
        String name = Command.SAVE_EVENT_TEACHER_NAME.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkDuration(String command) {
        String name = Command.SAVE_EVENT_DURATION.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEventDay(String command) {
        String name = Command.SAVE_EVENT_DAY.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEditSpecificExistingEvent(String command) {
        String name = Command.EDIT_SPECIFIC_EXISTING_EVENT.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEditSpecificEventField(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_FIELD.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditSpecificEventTeacherName(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_TEACHER.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }


    private String checkEditSpecificEventLessonStartTime(String command) {
        String name = Command.SHOW_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSpecificEventChooseOperation(String command) {
        String name = Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditScheduleEventStartTime(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkAddTimeToLessonSchedule(String command) {
        String name = Command.ADD_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkAddDaySpecificStartTime(String command) {
        String name = Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSetExtraDaySpecificStartTime(String command) {
        String name = Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkDeleteSpecificEventStartTime2(String command) {
        String name = Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkInlineEditSpecificEventDuration(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_DURATION.getName();
        if (command.contains(name)) {
            return command.split(LessonService.COLON_SEPARATOR)[0];
        }
        return command;
    }
}

