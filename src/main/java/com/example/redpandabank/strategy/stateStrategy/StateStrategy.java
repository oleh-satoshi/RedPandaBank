package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.model.Child;

public interface StateStrategy {
  StateHandler get(Child child);
}
