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
    final String MONDAY = "monday";
    final String TUESDAY = "tuesday";
    final String WEDNESDAY = "wednesday";
    final String THURSDAY = "thursday";
    final String FRIDAY = "friday";
    final String SATURDAY = "saturday";

    public InlineScheduleShowAllDaysButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(MONDAY), WeekDay.MONDAY.getDay())
                .extraButton(translateService.getBySlug(TUESDAY), WeekDay.TUESDAY.getDay())
                .extraButton(translateService.getBySlug(WEDNESDAY), WeekDay.WEDNESDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(THURSDAY), WeekDay.THURSDAY.getDay())
                .extraButton(translateService.getBySlug(FRIDAY), WeekDay.FRIDAY.getDay())
                .extraButton(translateService.getBySlug(SATURDAY), WeekDay.SATURDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK),
                        Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
