package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.commandStrategy.handler.BackToMainMenuCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.*;
import com.example.redpandabank.strategy.commandStrategy.handler.SchedulePlugCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.ScheduleStartCommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandStrategyImpl implements CommandStrategy {
    private Map<String, CommandHandler> commandStrategyMap;
    final ReplyMainMenuButton replyMainMenuButton;
    final ChildService childService;
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final LessonService lessonService;


    public CommandStrategyImpl(ReplyMainMenuButton replyMainMenuButton, ChildService childService,
                               InlineScheduleMenuButton inlineScheduleMenuButton, LessonService lessonService) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.childService = childService;
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.lessonService = lessonService;

        commandStrategyMap = new HashMap<>();
        commandStrategyMap.put(Command.START.getName(), new ScheduleStartCommandHandler(this.replyMainMenuButton, this.childService));
        commandStrategyMap.put(Command.SCHEDULE.getName(), new ScheduleMenuShowCommandHandler(this.inlineScheduleMenuButton));
        commandStrategyMap.put(Command.TO_MAIN_MENU.getName(), new BackToMainMenuCommandHandler(this.replyMainMenuButton));
        commandStrategyMap.put(Command.DELETE_EVENT.getName(), new ScheduleDeleteEventCommandHandler(this.lessonService));
        commandStrategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(), new EditScheduleEventCommandHandler(this.lessonService));
    }

    @Override
    public CommandHandler get(String command) {
        command = checkSaveEventName(command);
        command = checkSaveEventTeacher(command);
        command = checkSaveEventDuration(command);
        CommandHandler commandHandler = commandStrategyMap.get(command);
        if (commandHandler == null) {
            commandHandler = new SchedulePlugCommandHandler();
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
}