package com.example.redpandabank.strategy.mainCommandHandler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainCommandHandler {
    BotApiMethod<?> handle(Update update); 

    boolean isApplicable(Update update);
}