package com.example.redpandabank.model;

import lombok.Getter;

@Getter
public enum Command {
    START("/start");
    private final String name;

    Command(String name) {
        this.name = name;
    }
}
