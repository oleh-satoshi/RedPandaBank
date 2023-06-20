package com.example.redpandabank.keyboard;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.util.Separator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineChooseLanguage implements Pressable<InlineKeyboardMarkup> {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("English", Commands.SET_LANGUAGE.getName()
                        + Separator.COLON_SEPARATOR + "ENG")
                .endRow()
                .build();
    }
}
