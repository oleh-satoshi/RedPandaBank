package com.example.redpandabank.strategy.commandStrategy.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface CommandHandler<T> {
    BotApiMethod<?> handle(T update);
}
