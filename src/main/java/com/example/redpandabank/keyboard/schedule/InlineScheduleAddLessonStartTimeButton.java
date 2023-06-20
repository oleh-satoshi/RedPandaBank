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
public class InlineScheduleAddLessonStartTimeButton implements Pressable {
    final TranslateService translateService;
    static final String ADD_TIME = "add-time-lesson";

    public InlineScheduleAddLessonStartTimeButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(ADD_TIME),
                        Commands.SET_SAVE_EVENT_TIME_STATE.getName())
                .endRow()
                .build();
    }
}
