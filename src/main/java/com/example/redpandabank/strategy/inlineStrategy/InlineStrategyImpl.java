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
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleAddEventDuration;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleEdit;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleChooseEventByDay;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.*;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineStrategyImpl implements InlineStrategy {
    Map<String, InlineHandler> strategyMap;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday;
    final InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton;
    final InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder;
    final InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton;
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final InlineScheduleEditMenuButton inlineScheduleEditMenuButton;
    final ChildService childService;
    final InlineScheduleAddEventDay inlineScheduleAddEventDay;
    final InlineScheduleEditSpecificEventStartTimeChooseOperationButton chooseOperationButton;
    final InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton;
    final InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton;
    final ReplyMainMenuButton mainMenuButton;
    final InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button;


    public InlineStrategyImpl(LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              MessageSender messageSender,
                              InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday,
                              InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton,
                              InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder,
                              InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton, InlineScheduleMenuButton inlineScheduleMenuButton,
                              InlineScheduleEditMenuButton inlineScheduleEditMenuButton,
                              ChildService childService, InlineScheduleAddEventDay inlineScheduleAddEventDay,
                              InlineScheduleEditSpecificEventStartTimeChooseOperationButton chooseOperationButton,
                              InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton,
                              InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton,
                              ReplyMainMenuButton mainMenuButton,
                              InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.inlineScheduleAddEventByWeekday = inlineScheduleAddEventByWeekday;
        this.inlineScheduleEditEventFieldButton = inlineScheduleEditEventFieldButton;
        this.inlineKeyboardMarkupBuilder = inlineKeyboardMarkupBuilder;
        this.inlineScheduleShowAllDaysButton = inlineScheduleShowAllDaysButton;
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.inlineScheduleEditMenuButton = inlineScheduleEditMenuButton;
        this.childService = childService;
        this.inlineScheduleAddEventDay = inlineScheduleAddEventDay;
        this.chooseOperationButton = chooseOperationButton;
        this.inlineScheduleAddDaySpecificEventStartTimeButton = inlineScheduleAddDaySpecificEventStartTimeButton;
        this.inlineScheduleAddExtraDaySpecificStartTimeButton = inlineScheduleAddExtraDaySpecificStartTimeButton;
        this.mainMenuButton = mainMenuButton;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
        strategyMap = new HashMap<>();
        strategyMap.put(Command.TO_MAIN_MENU.getName(), new InlineToMainMenu(mainMenuButton));
        strategyMap.put(Command.SAVE_EVENT_MONDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_TUESDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_WEDNESDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_THURSDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_FRIDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_SATURDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_SUNDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService));
        strategyMap.put(WeekDay.MONDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.TUESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.THURSDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.FRIDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SATURDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SUNDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(Command.DELETE_EVENT.getName(), new InlineScheduleDeleteEvent(lessonService));
        strategyMap.put(Command.DELETE_EVENT_BY_ID.getName(), new InlineScheduleDeleteEventStep2(lessonScheduleService, lessonService, messageSender));
        strategyMap.put(Command.RECOVER_EVENT_BY_ID.getName(), new InlineScheduleRecoverEvent(lessonService));
        strategyMap.put(Command.CHOOSE_EVENT_BY_DAY.getName(), new InlineScheduleChooseEventByDay(inlineScheduleShowAllDaysButton));
        strategyMap.put(Command.EDIT_SCHEDULE.getName(), new InlineScheduleEdit(inlineScheduleEditMenuButton));
        strategyMap.put(Command.SCHEDULE.getName(), new InlineShowMainMenu(inlineScheduleMenuButton));
        strategyMap.put(Command.SAVE_EVENT_NAME.getName(), new InlineScheduleAddTitleEvent(childService));
        strategyMap.put(Command.SAVE_EVENT_TIME.getName(), new InlineScheduleSaveEventTime(childService));
        strategyMap.put(Command.SAVE_EVENT_TEACHER_NAME.getName(), new InlineScheduleAddTeacherName(childService, lessonService));
        strategyMap.put(Command.ADD_SCHEDULE_EVENT.getName(), new InlineScheduleAddTitleEvent(childService));
        strategyMap.put(Command.SAVE_EVENT_DURATION.getName(), new InlineScheduleAddEventDuration(lessonService, childService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TITLE.getName(), new InlineScheduleChangeEventTitle(lessonService, childService));
        strategyMap.put(Command.EDIT_EVENT_TEACHER_NAME.getName(), new InlineScheduleChangeTeacher(lessonService, childService));
        strategyMap.put(Command.EDIT_EVENT_DURATION.getName(), new InlineScheduleChangeDuration(lessonService, childService));
        strategyMap.put(Command.SAVE_EVENT_DAY.getName(), new InlineScheduleSaveEventDay(lessonService, childService, inlineScheduleAddEventDay));
        strategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(), new InlineScheduleEditEvent(lessonService));
        strategyMap.put(Command.EDIT_SPECIFIC_EXISTING_EVENT.getName(), new InlineScheduleEditSpecificExistingEvent(lessonService, lessonScheduleService, inlineScheduleEditEventFieldButton, inlineKeyboardMarkupBuilder));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_FIELD.getName(), new InlineScheduleEditEventField(lessonService, childService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TEACHER.getName(), new InlineScheduleEditEvenTeacherName(lessonService, childService));
        strategyMap.put(Command.SHOW_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleEditEventLessonStartTime(lessonService, childService, chooseOperationButton));
        strategyMap.put(Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName(), new InlineScheduleEditSpecificEventStartTimeChooseOperation(chooseOperationButton, lessonService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_START_TIME.getName(), new InlineScheduleEditEventLessonStartTime(lessonService, childService, chooseOperationButton));
        strategyMap.put(Command.ADD_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddTimeToLesson(lessonService, childService));
        strategyMap.put(Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddDaySpecificEventStartTime(lessonScheduleService, lessonService, childService, inlineScheduleAddDaySpecificEventStartTimeButton));
        strategyMap.put(Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineScheduleAddExtraDaySpecificStartTimeButton));
        strategyMap.put(Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineSetExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineScheduleAddDaySpecificEventStartTimeButton));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleDeleteSpecificEventStartTime(lessonService));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName(), new InlineScheduleDeleteSpecificEventStartTime2(lessonService, lessonScheduleService, specificEventStartTime2Button));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_DURATION.getName(), new InlineScheduleEditSpecificEventDuration(lessonService, childService));
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
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditSpecificEventTeacherName(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_TEACHER.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }


    private String checkEditSpecificEventLessonStartTime(String command) {
        String name = Command.SHOW_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSpecificEventChooseOperation(String command) {
        String name = Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditScheduleEventStartTime(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkAddTimeToLessonSchedule(String command) {
        String name = Command.ADD_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkAddDaySpecificStartTime(String command) {
        String name = Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSetExtraDaySpecificStartTime(String command) {
        String name = Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkDeleteSpecificEventStartTime2(String command) {
        String name = Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkInlineEditSpecificEventDuration(String command) {
        String name = Command.EDIT_SCHEDULE_EVENT_DURATION.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }
}

