package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ShowLessonsListCommandHandler implements CommandHandler<Update> {
    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
