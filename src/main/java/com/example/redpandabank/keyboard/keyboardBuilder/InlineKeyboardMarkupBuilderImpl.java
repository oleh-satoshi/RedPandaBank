package com.example.redpandabank.keyboard.keyboardBuilder;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineKeyboardMarkupBuilderImpl extends InlineKeyboardMarkupBuilder implements KeyboardMarkupBuilder {

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
        this.row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkupBuilderImpl button(String text, String callbackData) {
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setCallbackData(callbackData);
        keyboardButton.setText(text);
        this.row = new ArrayList<>();
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
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }

    @Override
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
