package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleRepeatAddLessonButton implements Pressable {
    final TranslateService translateService;
    final String TRY_AGAIN = "try-again";

    public InlineScheduleRepeatAddLessonButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(TRY_AGAIN),
                        Command.ADD_SCHEDULE_EVENT.getName())
                .endRow()
                .build();
    }
}
