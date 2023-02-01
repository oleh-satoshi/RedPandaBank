package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleShowAllDaysButton implements Pressable<InlineKeyboardMarkup> {
    final TranslateService translateService;
    final String BACK = "back";

    public InlineScheduleShowAllDaysButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(), WeekDay.MONDAY.getDay())
                .extraButton(WeekDay.TUESDAY.getDay(),WeekDay.TUESDAY.getDay())
                .extraButton(WeekDay.WEDNESDAY.getDay(), WeekDay.WEDNESDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.THURSDAY.getDay(),WeekDay.THURSDAY.getDay())
                .extraButton(WeekDay.FRIDAY.getDay(), WeekDay.FRIDAY.getDay())
                .extraButton(WeekDay.SATURDAY.getDay(),WeekDay.SATURDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK),
                        Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
