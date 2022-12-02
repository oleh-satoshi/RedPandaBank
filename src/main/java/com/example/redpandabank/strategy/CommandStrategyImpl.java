package com.example.redpandabank.strategy;

import com.example.redpandabank.model.Command;
import com.example.redpandabank.strategy.handler.CommandHandler;
import com.example.redpandabank.strategy.handler.StartCommandHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandStrategyImpl implements CommandStrategy {
    private Map<String, CommandHandler> strategyMap;

    public CommandStrategyImpl() {
        strategyMap = Map.of(Command.START.getName(),
                new StartCommandHandler());
    }


    @Override
    public CommandHandler get(String command) {
        CommandHandler commandHandler = strategyMap.get(command);
        return commandHandler;
    }
}