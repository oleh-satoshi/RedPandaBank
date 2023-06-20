package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.InlineChooseLanguage;
import com.example.redpandabank.keyboard.InlineStartInitButton;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
//import com.example.redpandabank.keyboard.schedule.InlineScheduleEdit;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.scheduleInline.*;
import com.example.redpandabank.util.Separator;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineStrategyImpl implements InlineStrategy {
    Map<String, InlineHandler> strategyMap;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday;
    final InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton;
    final InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton;
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final InlineScheduleEditMenuButton inlineScheduleEditMenuButton;
    final ChildService childService;
    final InlineScheduleAddEventDay inlineScheduleAddEventDay;
    final InlineScheduleEditSpecificEventStartTimeButton
            chooseOperationButton;
    final InlineScheduleAddExtraDaySpecificStartTimeButton
            inlineScheduleAddExtraDaySpecificStartTimeButton;
    final ReplyMainMenuButton mainMenuButton;
    final InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button;
    final TranslateService translateService;
    final InlineChooseLanguage inlineChooseLanguage;
    final InlineStartInitButton inlineStartInitButton;
    final InlineScheduleAddLessonStartTimeButton inlineScheduleAddLessonStartTimeButton;
    final InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton;

    public InlineStrategyImpl(LessonService lessonService,
                              LessonScheduleService lessonScheduleService,
                              MessageSender messageSender,
                              InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday,
                              InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton,
                              InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton,
                              InlineScheduleMenuButton inlineScheduleMenuButton,
                              InlineScheduleEditMenuButton inlineScheduleEditMenuButton,
                              ChildService childService,
                              InlineScheduleAddEventDay inlineScheduleAddEventDay,
                              InlineScheduleEditSpecificEventStartTimeButton chooseOperationButton,
                              InlineScheduleAddExtraDaySpecificStartTimeButton
                                      inlineScheduleAddExtraDaySpecificStartTimeButton,
                              ReplyMainMenuButton mainMenuButton,
                              InlineScheduleDeleteSpecificEventStartTime2Button
                                      specificEventStartTime2Button,
                              TranslateService translateService,
                              InlineChooseLanguage inlineChooseLanguage,
                              InlineStartInitButton inlineStartInitButton,
                              InlineScheduleAddLessonStartTimeButton inlineScheduleAddLessonStartTimeButton,
                              InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.inlineScheduleAddEventByWeekday = inlineScheduleAddEventByWeekday;
        this.inlineScheduleEditEventFieldButton = inlineScheduleEditEventFieldButton;
        this.inlineScheduleShowAllDaysButton = inlineScheduleShowAllDaysButton;
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.inlineScheduleEditMenuButton = inlineScheduleEditMenuButton;
        this.childService = childService;
        this.inlineScheduleAddEventDay = inlineScheduleAddEventDay;
        this.chooseOperationButton = chooseOperationButton;
        this.inlineScheduleAddExtraDaySpecificStartTimeButton
                = inlineScheduleAddExtraDaySpecificStartTimeButton;
        this.mainMenuButton = mainMenuButton;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
        this.translateService = translateService;
        this.inlineChooseLanguage = inlineChooseLanguage;
        this.inlineStartInitButton = inlineStartInitButton;
        this.inlineScheduleAddLessonStartTimeButton = inlineScheduleAddLessonStartTimeButton;
        this.inlineScheduleAddEventTimeAndDayButton = inlineScheduleAddEventTimeAndDayButton;

        strategyMap = new HashMap<>();
        strategyMap.put(Commands.TO_MAIN_MENU.getName(),
                new InlineToMainMenu(mainMenuButton, translateService));
        strategyMap.put(Commands.SAVE_EVENT_MONDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_TUESDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_WEDNESDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_THURSDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_FRIDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_SATURDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(Commands.SAVE_EVENT_SUNDAY.getName(),
                new InlineScheduleWeekdayButton(lessonService,
                        lessonScheduleService, translateService));
        strategyMap.put(WeekDay.MONDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.TUESDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.THURSDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.FRIDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.SATURDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.SUNDAY.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(),
                new InlineScheduleFindLessonByDay(lessonService, mainMenuButton, messageSender));
        strategyMap.put(Commands.DELETE_EVENT.getName(),
                new InlineScheduleDeleteEvent(lessonService, translateService, messageSender));
        strategyMap.put(Commands.DELETE_EVENT_BY_ID.getName(),
                new InlineScheduleDeleteEventStep2(
                        lessonScheduleService, lessonService,
                        messageSender, translateService));
        strategyMap.put(Commands.RECOVER_EVENT_BY_ID.getName(),
                new InlineScheduleRecoverEvent(lessonService, translateService, messageSender));
        strategyMap.put(Commands.CHOOSE_EVENT_BY_DAY.getName(),
                new InlineScheduleChooseEventByDay(
                        inlineScheduleShowAllDaysButton, translateService));
        strategyMap.put(Commands.EDIT_SCHEDULE.getName(),
                new InlineScheduleEdit(
                        inlineScheduleEditMenuButton, translateService));
        strategyMap.put(Commands.SCHEDULE.getName(),
                new InlineShowMainMenu(inlineScheduleMenuButton, translateService, messageSender));
        strategyMap.put(Commands.SET_SAVE_EVENT_NAME_STATE.getName(),
                new InlineScheduleAddTitleEvent(messageSender, childService, translateService));
        strategyMap.put(Commands.SET_SAVE_EVENT_TIME_STATE.getName(),
                new InlineScheduleSaveEventTime(childService, translateService, messageSender, lessonService));
        strategyMap.put(Commands.SET_SAVE_EVENT_TEACHER_NAME_STATE.getName(),
                new InlineScheduleAddTeacherName(childService,
                        lessonService, translateService, messageSender));
        strategyMap.put(Commands.ADD_SCHEDULE_EVENT.getName(),
                new InlineScheduleAddTitleEvent(messageSender, childService, translateService));
        strategyMap.put(Commands.SET_SAVE_EVENT_DURATION_STATE.getName(),
                new InlineScheduleAddEventDuration(
                        lessonService, childService, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EVENT_TITLE.getName(),
                new InlineScheduleChangeEventTitle(
                        lessonService, childService, translateService, messageSender));
        strategyMap.put(Commands.EDIT_EVENT_TEACHER_NAME.getName(),
                new InlineScheduleChangeTeacher(childService, translateService, messageSender));
        strategyMap.put(Commands.EDIT_EVENT_DURATION.getName(),
                new InlineScheduleChangeDuration(childService, translateService, messageSender));
        strategyMap.put(Commands.SAVE_EVENT_DAY.getName(),
                new InlineScheduleSaveEventDay(childService,
                        inlineScheduleAddEventDay, translateService, messageSender));
        strategyMap.put(Commands.SAVE_SPECIFIC_EVENT_DAY.getName(),
                new InlineScheduleSaveSpecificEventDay(childService, lessonService,
                        lessonScheduleService, inlineScheduleAddLessonStartTimeButton, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EXISTING_EVENT.getName(),
                new InlineScheduleEditEvent(lessonService, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SPECIFIC_EXISTING_EVENT.getName(),
                new InlineScheduleEditSpecificExistingEvent(
                        lessonService,
                        inlineScheduleEditEventFieldButton,
                        translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EVENT_FIELD.getName(),
                new InlineScheduleEditEventField(lessonService,
                        childService, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EVENT_TEACHER.getName(),
                new InlineScheduleEditEvenTeacherName(lessonService,
                        childService, translateService, messageSender));
        strategyMap.put(Commands.SHOW_SPECIFIC_EVENT_START_TIME.getName(),
                new InlineScheduleEditEventLessonStartTime(
                        lessonService, childService,
                        chooseOperationButton, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName(),
                new InlineScheduleEditSpecificEventStartTimeChooseOperation(
                        chooseOperationButton, lessonService,
                        translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EVENT_START_TIME.getName(),
                new InlineScheduleEditEventLessonStartTime(
                        lessonService, childService,
                        chooseOperationButton, translateService, messageSender));
        strategyMap.put(Commands.SET_ADD_SPECIFIC_EVENT_START_TIME_STATE.getName(),
                new InlineScheduleAddTimeToLesson(
                        lessonService, childService, translateService, messageSender));
        strategyMap.put(Commands.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(),
                new InlineScheduleAddExtraDaySpecificStartTime(
                        lessonService, lessonScheduleService, childService,
                        inlineScheduleAddExtraDaySpecificStartTimeButton,
                        translateService));
        strategyMap.put(Commands.DELETE_SPECIFIC_EVENT_START_TIME.getName(),
                new InlineScheduleDeleteSpecificEventStartTime(lessonService,
                        translateService, messageSender));
        strategyMap.put(Commands.DELETE_SPECIFIC_EVENT_START_TIME_2.getName(),
                new InlineScheduleDeleteSpecificEventStartTime2(
                        lessonService, lessonScheduleService,
                        specificEventStartTime2Button, translateService, messageSender));
        strategyMap.put(Commands.EDIT_SCHEDULE_EVENT_DURATION.getName(),
                new InlineScheduleEditSpecificEventDuration(
                        lessonService, childService, translateService, messageSender));
        strategyMap.put(Commands.SET_LANGUAGE.getName(),
                new SetLanguageInlineHandler(childService, translateService,
                        mainMenuButton, inlineStartInitButton));
        strategyMap.put(Commands.START_INIT.getName(),
                new InlineStartInit(translateService, mainMenuButton, messageSender));
        strategyMap.put(Commands.EDIT_EVENT_DAY.getName(),
                new InlineEditSpecificEventDay(lessonService, lessonScheduleService, messageSender, translateService));
        strategyMap.put(Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName(),
                new InlineScheduleAddExtraDaySpecificStartTime(lessonService,
                        lessonScheduleService, childService, inlineScheduleAddExtraDaySpecificStartTimeButton,
                        translateService));
        strategyMap.put(Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName(),
                new InlineScheduleSetExtraDayForSpecificEvent(lessonService, lessonScheduleService, messageSender, this.inlineScheduleAddEventTimeAndDayButton, translateService));

    }

    @Override
    public InlineHandler get(String command) {
        command = checkDeleteEventById(command);
        command = checkEditEventField(command);
        command = checkRecoverEvent(command);
        command = checkTeacherName(command);
        command = checkDuration(command);
        command = checkEventDay(command);
        command = checkSpecificEventDay(command);
        command = checkEditSpecificExistingEvent(command);
        command = checkEditSpecificEventField(command);
        command = checkEditSpecificEventTeacherName(command);
        command = checkEditSpecificEventLessonStartTime(command);
        command = checkSpecificEventChooseOperation(command);
        command = checkEditScheduleEventStartTime(command);
        command = checkSetAddSpecificStartTimeToLessonSchedule(command);
        command = checkAddDaySpecificStartTime(command);
        command = checkSetExtraDaySpecificStartTime(command);
        command = checkDeleteSpecificEventStartTime2(command);
        command = checkInlineEditSpecificEventDuration(command);
        command = checkSetLanguageCommandHandler(command);
        command = checkEditEventDay(command);

        InlineHandler inlineHandler = strategyMap.get(command);
        if (inlineHandler == null) {
            inlineHandler = new InlinePlug(translateService, messageSender);
        }
        return inlineHandler;
    }

    private String checkDeleteEventById(String command) {
        String name = Commands.DELETE_EVENT_BY_ID.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEditEventField(String command) {
        String name = StateCommands.EDIT_SPECIFIC_EVENT_FIELD.getState();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkRecoverEvent(String command) {
        String name = Commands.RECOVER_EVENT_BY_ID.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkTeacherName(String command) {
        String name = Commands.SET_SAVE_EVENT_TEACHER_NAME_STATE.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkDuration(String command) {
        String name = Commands.SET_SAVE_EVENT_DURATION_STATE.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEventDay(String command) {
        String day = Commands.SAVE_EVENT_DAY.getName();
        if (command.contains(day) && !day.equals(command)) {
            return command.substring(0, day.length());
        }
        return command;
    }

    private String checkSpecificEventDay(String command) {
        for (WeekDay weekDay : WeekDay.values()) {
            if (command.contains(weekDay.getDay())) {
                return command.split(Separator.COLON_SEPARATOR)[0];
            }
        }
        return command;
    }

    private String checkEditSpecificExistingEvent(String command) {
        String name = Commands.EDIT_SPECIFIC_EXISTING_EVENT.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }

    private String checkEditSpecificEventField(String command) {
        String name = Commands.EDIT_SCHEDULE_EVENT_FIELD.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditSpecificEventTeacherName(String command) {
        String name = Commands.EDIT_SCHEDULE_EVENT_TEACHER.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditSpecificEventLessonStartTime(String command) {
        String name = Commands.SHOW_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSpecificEventChooseOperation(String command) {
        String name = Commands.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName();
        if (command.contains(name)) {
            if (command.split(Separator.QUOTE_SEPARATOR).length == 3) {
                return command.split(Separator.QUOTE_SEPARATOR)[0];
            }
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditScheduleEventStartTime(String command) {
        String name = Commands.EDIT_SCHEDULE_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSetAddSpecificStartTimeToLessonSchedule(String command) {
        String name = Commands.SET_ADD_SPECIFIC_EVENT_START_TIME_STATE.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkAddDaySpecificStartTime(String command) {
        String name = Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSetExtraDaySpecificStartTime(String command) {
        command = command.split(Separator.COLON_SEPARATOR)[0];
        String name = Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName();
        if (name.contains(command)) {
            return name;
        }
        return command;
    }

    private String checkDeleteSpecificEventStartTime2(String command) {
        String name = Commands.DELETE_SPECIFIC_EVENT_START_TIME_2.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkInlineEditSpecificEventDuration(String command) {
        String name = Commands.EDIT_SCHEDULE_EVENT_DURATION.getName();
        if (command.contains(name)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkSetLanguageCommandHandler(String command) {
        String commandSetLanguage = Commands.SET_LANGUAGE.getName();
        if (command.contains(commandSetLanguage)) {
            return command.split(Separator.COLON_SEPARATOR)[0];
        }
        return command;
    }

    private String checkEditEventDay(String command) {
        String name = Commands.EDIT_EVENT_DAY.getName();
        if (command.contains(name) && !name.equals(command)) {
            return command.substring(0, name.length());
        }
        return command;
    }
}
