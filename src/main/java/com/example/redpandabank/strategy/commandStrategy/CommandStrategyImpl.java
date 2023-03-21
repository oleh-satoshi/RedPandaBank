package com.example.redpandabank.strategy.commandStrategy;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.InlineChooseLanguage;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.commandStrategy.handler.BackToMainMenuCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.SchedulePlugCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.EditScheduleEventCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.ScheduleDeleteEventCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.ScheduleMenuShowCommandHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class CommandStrategyImpl implements CommandStrategy {
    Map<String, CommandHandler> commandStrategyMap;
    final ReplyMainMenuButton replyMainMenuButton;
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final LessonService lessonService;
    final TranslateService translateService;

    public CommandStrategyImpl(ReplyMainMenuButton replyMainMenuButton, ChildService childService,
                               InlineScheduleMenuButton inlineScheduleMenuButton,
                               LessonService lessonService, TranslateService translateService,
                               InlineChooseLanguage inlineChooseLanguage) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.lessonService = lessonService;
        this.translateService = translateService;

        commandStrategyMap = new HashMap<>();
        commandStrategyMap.put(Command.SCHEDULE.getName(),
                new ScheduleMenuShowCommandHandler(inlineScheduleMenuButton, translateService));
        commandStrategyMap.put(Command.TO_MAIN_MENU.getName(),
                new BackToMainMenuCommandHandler(replyMainMenuButton, translateService));
        commandStrategyMap.put(Command.DELETE_EVENT.getName(),
                new ScheduleDeleteEventCommandHandler(lessonService, translateService));
        commandStrategyMap.put(Command.EDIT_SCHEDULE_EXISTING_EVENT.getName(),
                new EditScheduleEventCommandHandler(lessonService, translateService));
        commandStrategyMap.put(Command.START.getName(),
                new LanguageCommandHandler(
                        inlineChooseLanguage, childService, translateService));
    }

    @Override
    public CommandHandler get(String command) {
        command = checkSaveEventName(command);
        command = checkSaveEventTeacher(command);
        command = checkSaveEventDuration(command);
        CommandHandler commandHandler = commandStrategyMap.get(command);
        if (commandHandler == null) {
            commandHandler = new SchedulePlugCommandHandler(translateService);
        }
        return commandHandler;
    }

    private String checkSaveEventName(String command) {
        String commandName = Command.SAVE_EVENT_NAME.getName();
        if (command.contains(commandName) && !commandName.equals(command)) {
            String result = command.substring(0, 9);
            return result;
        }
        return command;
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
