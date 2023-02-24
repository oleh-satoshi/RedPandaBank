package com.example.redpandabank.strategy.inlineStrategy;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface InlineHandler<T> {
    BotApiMethod<?> handle(T update);
}
