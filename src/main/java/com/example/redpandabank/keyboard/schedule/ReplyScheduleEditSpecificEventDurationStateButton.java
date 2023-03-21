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
public class ReplyScheduleEditSpecificEventDurationStateButton implements Pressable {
    final TranslateService translateService;
    final String MADE_MISTAKE_DO_AGAIN = "made-mistake-do-again";
    final String FINISH = "finish";

    public ReplyScheduleEditSpecificEventDurationStateButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(MADE_MISTAKE_DO_AGAIN),
                        Command.EDIT_SCHEDULE_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(FINISH),
                        Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
