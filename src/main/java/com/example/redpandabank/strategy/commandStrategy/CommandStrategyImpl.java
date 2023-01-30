package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
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
    private final ReplyMainMenuButton replyMainMenuButton;
    private final InlineScheduleMenuButton inlineScheduleMenuButton;
    private final ReplyScheduleAddLessonNameButton replyScheduleAddLessonNameButton;
    private final MessageSender messageSender;
    private final LessonService lessonService;
    private final InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday;
    private final ChildService childService;
    private final InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton;
    private final LessonScheduleService lessonScheduleService;

    public CommandStrategyImpl(ReplyMainMenuButton replyMainMenuButton,
                               InlineScheduleMenuButton inlineScheduleMenuButton,
                               ReplyScheduleAddLessonNameButton replyScheduleAddLessonNameButton,
                               MessageSender messageSender,
                               LessonService lessonService,
                               InlineScheduleAddEventByWeekday inlineScheduleAddEventByWeekday,
                               ChildService childService,
                               InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton,
                               LessonScheduleService lessonScheduleService) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.replyScheduleAddLessonNameButton = replyScheduleAddLessonNameButton;
        this.messageSender = messageSender;
        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineScheduleAddEventByWeekday = inlineScheduleAddEventByWeekday;
        this.inlineScheduleShowAllDaysButton = inlineScheduleShowAllDaysButton;
        this.lessonScheduleService = lessonScheduleService;

        commandStrategyMap = new HashMap<>();
        commandStrategyMap.put(Command.START.getName(), new ScheduleStartCommandHandler(replyMainMenuButton, childService));
        commandStrategyMap.put(Command.SCHEDULE.getName(), new ScheduleMenuShowCommandHandler(inlineScheduleMenuButton));
        commandStrategyMap.put(Command.BACK_TO_MAIN_MENU.getName(), new BackToMainMenuCommandHandler(replyMainMenuButton));
        commandStrategyMap.put(Command.DELETE_EVENT.getName(), new ScheduleDeleteEventCommandHandler(lessonService));
        commandStrategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(), new EditScheduleEventCommandHandler(lessonService));
        commandStrategyMap.put(Command.SCHEDULE_2.getName(), new ScheduleMenuShowCommandHandler(inlineScheduleMenuButton));
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