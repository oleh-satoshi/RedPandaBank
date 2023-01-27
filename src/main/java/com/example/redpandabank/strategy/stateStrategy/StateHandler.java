package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface StateHandler<T> {
  BotApiMethod<?> handle(T update, TelegramBot telegramBot);
}