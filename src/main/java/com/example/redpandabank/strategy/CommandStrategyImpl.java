package com.example.redpandabank.strategy;

import com.example.redpandabank.buttons.main.MainMenuButton;
import com.example.redpandabank.buttons.schedule.ScheduleEditMenuButton;
import com.example.redpandabank.buttons.schedule.ScheduleMenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.strategy.handler.CommandHandler;
import com.example.redpandabank.strategy.handler.schedule.EditScheduleCommandHandler;
import com.example.redpandabank.strategy.handler.PlugCommandHandler;
import com.example.redpandabank.strategy.handler.StartCommandHandler;
import com.example.redpandabank.strategy.handler.schedule.ShowScheduleMenuCommandHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandStrategyImpl implements CommandStrategy {
    private Map<String, CommandHandler> strategyMap;
    private final MainMenuButton mainMenuButton;
    private final ScheduleMenuButton scheduleMenuButton;
    private final ScheduleEditMenuButton scheduleEditMenuButton;

    public CommandStrategyImpl(MainMenuButton mainMenuButton,
                               ScheduleMenuButton scheduleMenuButton,
                               ScheduleEditMenuButton scheduleEditMenuButton) {
        this.mainMenuButton = mainMenuButton;
        this.scheduleMenuButton = scheduleMenuButton;
        this.scheduleEditMenuButton = scheduleEditMenuButton;
        strategyMap = Map.of(Command.START.getName(), new StartCommandHandler(this.mainMenuButton),
                             Command.SCHEDULE.getName(), new ShowScheduleMenuCommandHandler(this.scheduleMenuButton),
                             Command.EDIT_SCHEDULE.getName(), new EditScheduleCommandHandler(this.scheduleEditMenuButton));
    }


    @Override
    public CommandHandler get(String command) {
        CommandHandler commandHandler = strategyMap.get(command);
        if (commandHandler == null) {
            commandHandler = new PlugCommandHandler();
        }
        return commandHandler;
    }
}