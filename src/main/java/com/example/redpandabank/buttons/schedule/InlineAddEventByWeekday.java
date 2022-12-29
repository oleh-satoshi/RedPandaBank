package com.example.redpandabank.buttons.schedule;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineAddEventByWeekday {
    public InlineKeyboardMarkup getInline() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton mondayButton = new InlineKeyboardButton();
        mondayButton.setText("Monday");
        mondayButton.setCallbackData("/scheduleMonday");

        InlineKeyboardButton tuesdayButton = new InlineKeyboardButton();
        tuesdayButton.setText("Tuesday");
        tuesdayButton.setCallbackData("/scheduleTuesday");

        InlineKeyboardButton wednesdayButton = new InlineKeyboardButton();
        wednesdayButton.setText("Wednesday");
        wednesdayButton.setCallbackData("/scheduleWednesday");

        InlineKeyboardButton thursdayButton = new InlineKeyboardButton();
        thursdayButton.setText("Thursday");
        thursdayButton.setCallbackData("/scheduleThursday");

        InlineKeyboardButton fridayButton = new InlineKeyboardButton();
        fridayButton.setText("Friday");
        fridayButton.setCallbackData("/scheduleFriday");

        InlineKeyboardButton saturdayButton = new InlineKeyboardButton();
        saturdayButton.setText("Saturday");
        saturdayButton.setCallbackData("/scheduleSaturday");

        InlineKeyboardButton sundayButton = new InlineKeyboardButton();
        sundayButton.setText("Sunday");
        sundayButton.setCallbackData("/scheduleSunday");

        List<InlineKeyboardButton> firstListButtons = new ArrayList<>();

        firstListButtons.add(mondayButton);
        firstListButtons.add(tuesdayButton);
        firstListButtons.add(wednesdayButton);
        firstListButtons.add(thursdayButton);
        firstListButtons.add(fridayButton);
        firstListButtons.add(saturdayButton);

        List<List<InlineKeyboardButton>> firstRowList = new ArrayList<>();

        firstRowList.add(firstListButtons);
//        firstRowList.add(secondListButtons);
        keyboardMarkup.setKeyboard(firstRowList);
        return keyboardMarkup;
    }
}
