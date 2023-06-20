package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddSpecificEventStartTimeButton implements Pressable {
    final TranslateService translateService;
    static final String MONDAY = "monday";
    static final String TUESDAY = "tuesday";
    static final String WEDNESDAY = "wednesday";
    static final String THURSDAY = "thursday";
    static final String FRIDAY = "friday";
    static final String SATURDAY = "saturday";

    public InlineScheduleAddSpecificEventStartTimeButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(MONDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.MONDAY.getDay())
                .extraButton(translateService.getBySlug(TUESDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(WEDNESDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.WEDNESDAY.getDay())
                .extraButton(translateService.getBySlug(THURSDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(FRIDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.FRIDAY.getDay())
                .extraButton(translateService.getBySlug(SATURDAY),
                        Commands.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}
