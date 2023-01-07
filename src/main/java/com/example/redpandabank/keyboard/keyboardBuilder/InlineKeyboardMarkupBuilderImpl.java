package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class InlineKeyboardMarkupBuilderImpl extends InlineKeyboardMarkupBuilder implements KeyboardMarkupBuilder {

    private List<InlineKeyboardButton> row;
    private final List<List<InlineKeyboardButton>> keyboard;

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

//    public InlineKeyboardMarkupBuilder buttonWithURL(String text, String URL) {
//        row.add(new InlineKeyboardButton()
//                .setText(text)
//                //.setUrl(URL));
//        return this;
//    }

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

    public EditMessageText rebuild(Long messageId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(toIntExact(messageId));
        editMessageText.setReplyMarkup(keyboardMarkup);
        return editMessageText;
    }

}