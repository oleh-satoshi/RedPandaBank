package com.example.redpandabank.keyboard;

public interface PressableWithArguments<T, F, S> {
    T getKeyboard(F object1, S object2);
}
