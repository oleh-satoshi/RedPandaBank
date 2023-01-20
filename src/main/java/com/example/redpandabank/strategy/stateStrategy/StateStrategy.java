package com.example.redpandabank.strategy.stateStrategy;


import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;

public interface StateStrategy {
  CommandHandler get(String command);
}