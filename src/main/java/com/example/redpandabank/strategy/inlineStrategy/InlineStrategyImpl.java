package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.keyboard.InlineChooseLanguage;
import com.example.redpandabank.keyboard.InlineStartInitButton;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.inlineStrategy.mainMenu.InlineToMainMenu;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleAddEventDuration;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleEdit;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.InlineScheduleChooseEventByDay;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.*;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
    final TranslateService translateService;
    final InlineChooseLanguage inlineChooseLanguage;
    final InlineStartInitButton inlineStartInitButton;

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
                              InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button,
                              TranslateService translateService, InlineChooseLanguage inlineChooseLanguage, InlineStartInitButton inlineStartInitButton) {
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
        this.translateService = translateService;
        this.inlineChooseLanguage = inlineChooseLanguage;
        this.inlineStartInitButton = inlineStartInitButton;

        strategyMap = new HashMap<>();
        strategyMap.put(Command.TO_MAIN_MENU.getName(), new InlineToMainMenu(mainMenuButton, this.translateService));
        strategyMap.put(Command.SAVE_EVENT_MONDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_TUESDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_WEDNESDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_THURSDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_FRIDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_SATURDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(Command.SAVE_EVENT_SUNDAY.getName(), new InlineScheduleWeekdayButton(lessonService, lessonScheduleService, translateService));
        strategyMap.put(WeekDay.MONDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.TUESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.THURSDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.FRIDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SATURDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.SUNDAY.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(), new InlineScheduleFindLessonByDay(lessonService, mainMenuButton));
        strategyMap.put(Command.DELETE_EVENT.getName(), new InlineScheduleDeleteEvent(lessonService, translateService));
        strategyMap.put(Command.DELETE_EVENT_BY_ID.getName(), new InlineScheduleDeleteEventStep2(lessonScheduleService, lessonService, messageSender, translateService));
        strategyMap.put(Command.RECOVER_EVENT_BY_ID.getName(), new InlineScheduleRecoverEvent(lessonService, translateService));
        strategyMap.put(Command.CHOOSE_EVENT_BY_DAY.getName(), new InlineScheduleChooseEventByDay(inlineScheduleShowAllDaysButton, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE.getName(), new InlineScheduleEdit(inlineScheduleEditMenuButton, translateService));
        strategyMap.put(Command.SCHEDULE.getName(), new InlineShowMainMenu(inlineScheduleMenuButton, translateService));
        strategyMap.put(Command.SAVE_EVENT_NAME.getName(), new InlineScheduleAddTitleEvent(childService, translateService));
        strategyMap.put(Command.SAVE_EVENT_TIME.getName(), new InlineScheduleSaveEventTime(childService, translateService));
        strategyMap.put(Command.SAVE_EVENT_TEACHER_NAME.getName(), new InlineScheduleAddTeacherName(childService, lessonService, translateService));
        strategyMap.put(Command.ADD_SCHEDULE_EVENT.getName(), new InlineScheduleAddTitleEvent(childService, translateService));
        strategyMap.put(Command.SAVE_EVENT_DURATION.getName(), new InlineScheduleAddEventDuration(lessonService, childService, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TITLE.getName(), new InlineScheduleChangeEventTitle(lessonService, childService, translateService));
        strategyMap.put(Command.EDIT_EVENT_TEACHER_NAME.getName(), new InlineScheduleChangeTeacher(childService, translateService));
        strategyMap.put(Command.EDIT_EVENT_DURATION.getName(), new InlineScheduleChangeDuration(childService, translateService));
        strategyMap.put(Command.SAVE_EVENT_DAY.getName(), new InlineScheduleSaveEventDay(lessonService, childService, inlineScheduleAddEventDay, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(), new InlineScheduleEditEvent(lessonService, translateService));
        strategyMap.put(Command.EDIT_SPECIFIC_EXISTING_EVENT.getName(), new InlineScheduleEditSpecificExistingEvent(lessonService, lessonScheduleService, inlineScheduleEditEventFieldButton, inlineKeyboardMarkupBuilder, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_FIELD.getName(), new InlineScheduleEditEventField(lessonService, childService, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_TEACHER.getName(), new InlineScheduleEditEvenTeacherName(lessonService, childService, translateService));
        strategyMap.put(Command.SHOW_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleEditEventLessonStartTime(lessonService, childService, chooseOperationButton, translateService));
        strategyMap.put(Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName(), new InlineScheduleEditSpecificEventStartTimeChooseOperation(chooseOperationButton, lessonService, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_START_TIME.getName(), new InlineScheduleEditEventLessonStartTime(lessonService, childService, chooseOperationButton, translateService));
        strategyMap.put(Command.ADD_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddTimeToLesson(lessonService, childService, translateService));
        strategyMap.put(Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddDaySpecificEventStartTime(lessonScheduleService, lessonService, childService, inlineScheduleAddDaySpecificEventStartTimeButton, translateService));
        strategyMap.put(Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleAddExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineScheduleAddExtraDaySpecificStartTimeButton, translateService));
        strategyMap.put(Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(), new InlineSetExtraDaySpecificStartTime(lessonService, lessonScheduleService, childService, inlineScheduleAddDaySpecificEventStartTimeButton, translateService));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME.getName(), new InlineScheduleDeleteSpecificEventStartTime(lessonService, translateService));
        strategyMap.put(Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName(), new InlineScheduleDeleteSpecificEventStartTime2(lessonService, lessonScheduleService, specificEventStartTime2Button, translateService));
        strategyMap.put(Command.EDIT_SCHEDULE_EVENT_DURATION.getName(), new InlineScheduleEditSpecificEventDuration(lessonService, childService, translateService));
        strategyMap.put(Command.SET_LANGUAGE.getName(), new SetLanguageInlineHandler(childService, translateService, mainMenuButton, this.inlineStartInitButton));
        strategyMap.put(Command.START_INIT.getName(), new InlineStartInit(translateService, mainMenuButton));
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
        command = checkSetLanguageCommandHandler(command);

        InlineHandler inlineHandler = strategyMap.get(command);
        if (inlineHandler == null) {
            inlineHandler = new InlinePlug(translateService);
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

    private String checkSetLanguageCommandHandler(String command) {
        String commandSetLanguage = Command.SET_LANGUAGE.getName();
        if (command.contains(commandSetLanguage)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }
}
