package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.WeekDay;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineShowAllDays {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Monday", WeekDay.MONDAY.getDay())
                .endRow()
                .row()
                .button("Tuesday",WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button("Wednesday", WeekDay.WEDNESDAY.getDay())
                .endRow()
                .row()
                .button("Thursday",WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button("Friday", WeekDay.FRIDAY.getDay())
                .endRow()
                .row()
                .button("Saturday",WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton mondayButton = new InlineKeyboardButton();
//        mondayButton.setText("Monday");
//        mondayButton.setCallbackData(WeekDay.MONDAY.getDay());
//
//        InlineKeyboardButton tuesdayButton = new InlineKeyboardButton();
//        tuesdayButton.setText("Tuesday");
//        tuesdayButton.setCallbackData(WeekDay.TUESDAY.getDay());
//
//        InlineKeyboardButton wednesdayButton = new InlineKeyboardButton();
//        wednesdayButton.setText("Wednesday");
//        wednesdayButton.setCallbackData(WeekDay.WEDNESDAY.getDay());
//
//        InlineKeyboardButton thursdayButton = new InlineKeyboardButton();
//        thursdayButton.setText("Thursday");
//        thursdayButton.setCallbackData(WeekDay.THURSDAY.getDay());
//
//        InlineKeyboardButton fridayButton = new InlineKeyboardButton();
//        fridayButton.setText("Friday");
//        fridayButton.setCallbackData(WeekDay.FRIDAY.getDay());
//
//        InlineKeyboardButton saturdayButton = new InlineKeyboardButton();
//        saturdayButton.setText("Saturday");
//        saturdayButton.setCallbackData(WeekDay.SATURDAY.getDay());
//
//        InlineKeyboardButton sundayButton = new InlineKeyboardButton();
//        sundayButton.setText("Sunday");
//        sundayButton.setCallbackData(WeekDay.SUNDAY.getDay());
//
//        List<InlineKeyboardButton> firstListButtons = new ArrayList<>();
//
//        firstListButtons.add(mondayButton);
//        firstListButtons.add(tuesdayButton);
//        firstListButtons.add(wednesdayButton);
//        firstListButtons.add(thursdayButton);
//        firstListButtons.add(fridayButton);
//        firstListButtons.add(saturdayButton);
//
//        List<List<InlineKeyboardButton>> firstRowList = new ArrayList<>();
//
//        firstRowList.add(firstListButtons);
//        keyboardMarkup.setKeyboard(firstRowList);
//        return keyboardMarkup;
    }
}
