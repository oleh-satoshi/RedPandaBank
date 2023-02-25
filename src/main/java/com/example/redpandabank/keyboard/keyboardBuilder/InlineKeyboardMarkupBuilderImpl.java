package com.example.redpandabank.keyboard.keyboardBuilder;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class InlineKeyboardMarkupBuilderImpl implements KeyboardMarkupBuilder {

    List<InlineKeyboardButton> row;
    final List<List<InlineKeyboardButton>> keyboard;

    private InlineKeyboardMarkupBuilderImpl() {
        row = new ArrayList<>();
        keyboard = new ArrayList<>();
    }

    public static InlineKeyboardMarkupBuilderImpl create() {
        return new InlineKeyboardMarkupBuilderImpl();
    }

    @Override
    public InlineKeyboardMarkupBuilderImpl row() {
        row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkupBuilderImpl button(String text, String callbackData) {
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setCallbackData(callbackData);
        keyboardButton.setText(text);
        row = new ArrayList<>();
        row.add(keyboardButton);
        return this;
    }

    public InlineKeyboardMarkupBuilderImpl extraButton(String text, String callbackData) {
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setCallbackData(callbackData);
        keyboardButton.setText(text);
        row.add(keyboardButton);
        return this;
    }

    @Override
    public InlineKeyboardMarkupBuilderImpl endRow() {
        keyboard.add(row);
        row = null;
        return this;
    }

    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public EditMessageText rebuild(Long messageId, String text) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(toIntExact(messageId));
        editMessageText.setReplyMarkup(keyboardMarkup);
        editMessageText.setText(text);
        return editMessageText;
    }
}
