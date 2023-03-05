package com.example.redpandabank.keyboard.main;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.builder.ReplyKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ReplyMainMenuButton implements Pressable {
    final TranslateService translateService;
    static final String MORE = "more";

    public ReplyMainMenuButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(Command.SCHEDULE.getName())
                .button(translateService.getBySlug(MORE))
                .endRow()
                .build();
    }
}
