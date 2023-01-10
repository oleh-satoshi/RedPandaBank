package com.example.redpandabank.service;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineStrategy;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@PackagePrivate
@Component
public class TelegramBot {
    Set<String> hashSet;
    final CommandStrategy commandStrategy;
    final InlineStrategy inlineStrategy;
    final ChildService childService;
    final LessonService lessonService;
    final static String SEPARATOR = ":";

    public TelegramBot(CommandStrategy commandStrategy,
                       InlineStrategy inlineStrategy,
                       ChildService childService, LessonService lessonService) {
        this.commandStrategy = commandStrategy;
        this.inlineStrategy = inlineStrategy;
        this.childService = childService;
        this.lessonService = lessonService;
        hashSet = new HashSet<>();
        hashSet.add(Command.EDIT_SCHEDULE_EVENT_FIELD.getName());
        hashSet.add(Command.EDIT_SCHEDULE_EVENT_TEACHER.getName());
        hashSet.add(Command.EDIT_SCHEDULE_EVENT_START_TIME.getName());
        hashSet.add(Command.SAVE_EVENT_DURATION.getName());
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Boolean hasReply = update.hasMessage() && update.getMessage().hasText();
        Boolean hasCallback = update.hasCallbackQuery();
        String replyCommand;
        if (hasReply) {
            replyCommand = update.getMessage().getText();
//            String[] split = replyCommand.split(SEPARATOR);
//            if (hashSet.contains(split[0])) {
//                Child child = childService.getById(Long.valueOf(split[2]));
//                if (!child.getState().equals(ChildService.NO_STATE)) {
//                    if (child.getState().equals(Command.EDIT_SCHEDULE_EVENT_FIELD.getName())) {
//                        lessonService.getById()
//                    }
//                }
//
//            }
            CommandHandler commandHandler = commandStrategy.get(replyCommand);
            return commandHandler.handle(update);
        } else if (hasCallback) {
            replyCommand = update.getCallbackQuery().getData();
            InlineHandler inlineHandler = inlineStrategy.get(replyCommand);
            return inlineHandler.handle(update);
        }
        return null;
    }
}
