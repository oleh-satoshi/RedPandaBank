package com.example.redpandabank.strategy.commandStrategy;


import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;

public interface CommandStrategy {
  CommandHandler get(String command);
}
