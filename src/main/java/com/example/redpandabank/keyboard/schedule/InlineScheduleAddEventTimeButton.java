package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventTimeButton implements Pressable {
    final TranslateService translateService;
    static final String ADD_ANOTHER_DAY_AND_TIME = "add-another-day-and-time";
    static final String DONE = "done";

    public InlineScheduleAddEventTimeButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(ADD_ANOTHER_DAY_AND_TIME),
                        Command.SAVE_EVENT_DAY.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(DONE),
                        Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
