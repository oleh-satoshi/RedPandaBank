package com.example.redpandabank.strategy.stateStrategy;


import com.example.redpandabank.model.Child;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;

public interface StateStrategy {
  StateHandler get(Child child);
}