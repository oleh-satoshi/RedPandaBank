package com.example.redpandabank.keyboard;

public interface PressableWithArgument<T, S> {
    T getKeyboard(S object);
}
