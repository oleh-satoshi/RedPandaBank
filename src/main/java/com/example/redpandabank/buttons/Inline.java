package com.example.redpandabank.buttons;

import org.apache.naming.factory.SendMailFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class Inline {
    public InlineKeyboardMarkup getInline() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();

        keyboardButton.setText("Monday");
        keyboardButton.setCallbackData("/scheduleMonday");
        List<InlineKeyboardButton> listButtons = new ArrayList<>();
        listButtons.add(keyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(listButtons);
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }
}
