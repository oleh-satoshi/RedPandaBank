package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventTimeAndDayButton implements Pressable {
    final TranslateService translateService;
    static final String ADD_ANOTHER_DAY_AND_TIME = "add-another-day-and-time";
    static final String DONE = "done";

    public InlineScheduleAddEventTimeAndDayButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .button(translateService.getBySlug(DONE),
                        Commands.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
