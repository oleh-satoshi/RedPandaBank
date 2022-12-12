package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.buttons.Inline;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.buttons.main.MainMenuButton;
import com.example.redpandabank.repository.ChildRepository;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.LessonTimeService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.buttons.schedule.AddLessonNameButton;
import com.example.redpandabank.buttons.schedule.EditMenuButton;
import com.example.redpandabank.buttons.schedule.MenuButton;
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
    private final ChildRepository childRepository;
    private final LessonService lessonService;
    private final LessonTimeService lessonTimeService;
    private final Inline inline;

    public CommandStrategyImpl(MainMenuButton mainMenuButton,
                               MenuButton menuButton,
                               EditMenuButton editMenuButton,
                               AddLessonNameButton addLessonNameButton,
                               MessageSender messageSender,
                               BackToMainMenuButton backToMainMenuButton,
                               ChildRepository childRepository,
                               LessonService lessonService,
                               LessonTimeService lessonTimeService, Inline inline) {
        this.mainMenuButton = mainMenuButton;
        this.menuButton = menuButton;
        this.editMenuButton = editMenuButton;
        this.addLessonNameButton = addLessonNameButton;
        this.messageSender = messageSender;
        this.backToMainMenuButton = backToMainMenuButton;
        this.childRepository = childRepository;
        this.lessonService = lessonService;
        this.lessonTimeService = lessonTimeService;
        this.inline = inline;

        strategyMap = new HashMap<>();
        strategyMap.put(Command.START.getName(), new StartCommandHandler(this.mainMenuButton, this.childRepository, backToMainMenuButton));
        strategyMap.put(Command.SCHEDULE.getName(), new ShowScheduleMenuCommandHandler(menuButton));
        strategyMap.put(Command.EDIT_SCHEDULE.getName(), new EditScheduleCommandHandler(editMenuButton, backToMainMenuButton));
        strategyMap.put(Command.ALL_LESSONS.getName(), new ShowLessonsListCommandHandler());
        strategyMap.put(Command.SAVE_EVENT_NAME.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.SAVE_EVENT_DESCRIPTION.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.ADD_SCHEDULE_EVENT.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.SAVE_EVENT_DURATION.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.SAVE_EVENT_SCHEDULE.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.SAVE_EVENT_MONDAY.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender, this.inline));
        strategyMap.put(Command.BACK_TO_MAIN_MENU.getName(), new BackToMainMenuCommandHandler(this.mainMenuButton));
    }


//                Command.START.getName(), new StartCommandHandler(this.mainMenuButton, this.childRepository),
//                Command.SCHEDULE.getName(), new ShowScheduleMenuCommandHandler(menuButton),
//                Command.EDIT_SCHEDULE.getName(), new EditScheduleCommandHandler(editMenuButton, backToMainMenuButton),
//                Command.ALL_LESSONS.getName(), new ShowLessonsListCommandHandler(),
//
//                Command.SAVE_EVENT_NAME.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//                Command.SAVE_EVENT_DESCRIPTION.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//                Command.ADD_SCHEDULE_EVENT.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//                Command.SAVE_EVENT_DURATION.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//                //Command.SAVE_EVENT_MONDAY.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//
//                Command.SAVE_EVENT_SCHEDULE.getName(), new AddScheduleEventCommandHandler(this.lessonService, this.lessonTimeService, this.backToMainMenuButton, this.messageSender),
//                Command.BACK_TO_MAIN_MENU.getName(), new BackToMainMenuCommandHandler(this.mainMenuButton));
//    }

    @Override
    public CommandHandler get(String command) {
        command = checkSaveEventName(command);
        command = checkSaveEventDescription(command);
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

    private String checkSaveEventDescription(String command) {
        String commandDecs = Command.SAVE_EVENT_DESCRIPTION.getName();
        if (command.contains(commandDecs) && !commandDecs.equals(command)) {
            return command.substring(0, 9);
        } else {
            return command;
        }
    }

    private String checkSaveEventDuration(String command) {
        String commandDuration = Command.SAVE_EVENT_DURATION.getName();
        if (command.contains(commandDuration) && !commandDuration.equals(command)) {
            return command.substring(0, 13);
        } else {
            return command;
        }
    }

    private String checkSaveEventSchedule(String command) {
        String commandSchedule = Command.SAVE_EVENT_SCHEDULE.getName();
        if (command.contains(commandSchedule) && !commandSchedule.equals(command)) {
            return command.substring(0, 13);
        } else {
            return command;
        }
    }
}