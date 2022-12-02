package com.example.redpandabank.strategy;


import com.example.redpandabank.strategy.handler.CommandHandler;

public interface CommandStrategy {
  CommandHandler get(String command);
}