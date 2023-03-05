package com.example.redpandabank.keyboard;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineStartInitButton implements Pressable<InlineKeyboardMarkup> {
    final TranslateService translateService;
    static final String START_INIT = "start-init";

    public InlineStartInitButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(START_INIT),
                        Command.START_INIT.getName())
                .endRow()
                .build();
    }
}
