package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.buttons.schedule.*;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.buttons.main.MainMenuButton;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.commandStrategy.handler.BackToMainMenuCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.*;
import com.example.redpandabank.strategy.commandStrategy.handler.PlugCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.StartCommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandStrategyImpl implements CommandStrategy {
    private Map<String, CommandHandler> strategyMap;
    private final MainMenuButton mainMenuButton;
    private final MenuButton menuButton;
    private final EditMenuButton editMenuButton;
    private final AddLessonNameButton addLessonNameButton;
    private final MessageSender messageSender;
    private final BackToMainMenuButton backToMainMenuButton;
    private final LessonService lessonService;
    private final InlineAddEventByWeekday inlineAddEventByWeekday;
    private final ChildService childService;
    private final InlineShowAllDays inlineShowAllDays;
    private final LessonScheduleService lessonScheduleService;

    public CommandStrategyImpl(MainMenuButton mainMenuButton,
                               MenuButton menuButton,
                               EditMenuButton editMenuButton,
                               AddLessonNameButton addLessonNameButton,
                               MessageSender messageSender,
                               BackToMainMenuButton backToMainMenuButton,
                               LessonService lessonService,
                               InlineAddEventByWeekday inlineAddEventByWeekday,
                               ChildService childService,
                               InlineShowAllDays inlineShowAllDays,
                               LessonScheduleService lessonScheduleService) {
        this.mainMenuButton = mainMenuButton;
        this.menuButton = menuButton;
        this.editMenuButton = editMenuButton;
        this.addLessonNameButton = addLessonNameButton;
        this.messageSender = messageSender;
        this.backToMainMenuButton = backToMainMenuButton;
        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
        this.inlineShowAllDays = inlineShowAllDays;
        this.lessonScheduleService = lessonScheduleService;

        strategyMap = new HashMap<>();
        strategyMap.put(Command.START.getName(), new StartCommandHandler(this.mainMenuButton, childService, backToMainMenuButton));
        strategyMap.put(Command.SCHEDULE.getName(), new ShowScheduleMenuCommandHandler(menuButton));
        strategyMap.put(Command.EDIT_SCHEDULE.getName(), new EditScheduleCommandHandler(editMenuButton, backToMainMenuButton));
        strategyMap.put(Command.SAVE_EVENT_NAME.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_TEACHER_NAME.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.ADD_SCHEDULE_EVENT.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_DURATION.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_SCHEDULE.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.SAVE_EVENT_MONDAY.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.backToMainMenuButton, this.messageSender, this.inlineAddEventByWeekday, this.lessonScheduleService));
        strategyMap.put(Command.BACK_TO_MAIN_MENU.getName(), new BackToMainMenuCommandHandler(this.mainMenuButton));
        strategyMap.put(Command.CHOOSE_EVENT_BY_DAY.getName(), new ChooseEventByDayCommandHandler(this.lessonService, this.inlineShowAllDays));
    }

    @Override
    public CommandHandler get(String command) {
        command = checkSaveEventName(command);
        command = checkSaveEventTeacher(command);
        command = checkSaveEventDuration(command);
        command = checkSaveEventSchedule(command);
        CommandHandler commandHandler = strategyMap.get(command);
        if (commandHandler == null) {
            commandHandler = new PlugCommandHandler();
        }
        return commandHandler;
    }



    private String checkSaveEventName(String command) {
        String commandName = Command.SAVE_EVENT_NAME.getName();
        if (command.contains(commandName) && !commandName.equals(command)) {
            String result = command.substring(0, 9);
            return  result;
        } else {
            return command;
        }
    }

    private String checkSaveEventTeacher(String command) {
        String commandTeacher = Command.SAVE_EVENT_TEACHER_NAME.getName();
        if (command.contains(commandTeacher) && !commandTeacher.equals(command)) {
            return command.substring(0, commandTeacher.length());
        }
        return command;
    }

    private String checkSaveEventDuration(String command) {
        String commandDuration = Command.SAVE_EVENT_DURATION.getName();
        if (command.contains(commandDuration) && !commandDuration.equals(command)) {
            return command.substring(0, commandDuration.length());
        }
        return command;
    }

    private String checkSaveEventSchedule(String command) {
        String commandSchedule = Command.SAVE_EVENT_SCHEDULE.getName();
        if (command.contains(commandSchedule) && !commandSchedule.equals(command)) {
            return command.substring(0, commandSchedule.length());
        }
        return command;
    }
}