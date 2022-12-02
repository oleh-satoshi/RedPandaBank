package com.example.redpandabank.strategy;

import com.example.redpandabank.buttons.main.MainMenuButton;
import com.example.redpandabank.buttons.schedule.AddLessonNameButton;
import com.example.redpandabank.buttons.schedule.EditMenuButton;
import com.example.redpandabank.buttons.schedule.MenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.handler.CommandHandler;
import com.example.redpandabank.strategy.handler.scheduleCommand.AddScheduleEventCommandHandler;
import com.example.redpandabank.strategy.handler.scheduleCommand.EditScheduleCommandHandler;
import com.example.redpandabank.strategy.handler.PlugCommandHandler;
import com.example.redpandabank.strategy.handler.StartCommandHandler;
import com.example.redpandabank.strategy.handler.scheduleCommand.ShowLessonsListCommandHandler;
import com.example.redpandabank.strategy.handler.scheduleCommand.ShowScheduleMenuCommandHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandStrategyImpl implements CommandStrategy {
    private Map<String, CommandHandler> strategyMap;
    private final MainMenuButton mainMenuButton;
    private final MenuButton menuButton;
    private final EditMenuButton editMenuButton;
    private final AddLessonNameButton addLessonNameButton;
    private final MessageSender messageSender;

    public CommandStrategyImpl(MainMenuButton mainMenuButton,
                               MenuButton menuButton,
                               EditMenuButton editMenuButton,
                               AddLessonNameButton addLessonNameButton,
                               MessageSender messageSender) {
        this.mainMenuButton = mainMenuButton;
        this.menuButton = menuButton;
        this.editMenuButton = editMenuButton;
        this.addLessonNameButton = addLessonNameButton;
        this.messageSender = messageSender;

        strategyMap = Map.of(Command.START.getName(), new StartCommandHandler(this.mainMenuButton),
                Command.SCHEDULE.getName(), new ShowScheduleMenuCommandHandler(menuButton),
                Command.EDIT_SCHEDULE.getName(), new EditScheduleCommandHandler(editMenuButton),
                Command.ALL_LESSONS.getName(), new ShowLessonsListCommandHandler(),
                Command.SAVE_EVENT_NAME.getName(), new AddScheduleEventCommandHandler(this.messageSender),
                Command.SAVE_EVENT_DESCRIPTION.getName(), new AddScheduleEventCommandHandler(this.messageSender),
                Command.ADD_SCHEDULE_EVENT.getName(), new AddScheduleEventCommandHandler(this.messageSender));
    }

    @Override
    public CommandHandler get(String command) {
        command = checkSaveEventName(command);
        command = checkSaveEventDescription(command);
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
            System.out.println(result);
            return  result;
        } else {
            return commandName;
        }
    }

    private String checkSaveEventDescription(String command) {
        String commandDecs = Command.SAVE_EVENT_DESCRIPTION.getName();
        if (commandDecs.contains(command) && !commandDecs.equals(command)) {
            return command.substring(0, 9);
        } else {
            return commandDecs;
        }
    }
}