package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineAddExtraDay {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Добавить время", Command.SAVE_EVENT_TIME.getName())
                .endRow()
                .build();

//        return InlineKeyboardMarkupBuilderImpl.create()
//                .row()
//                .button("Добавить день", Command.ADD_EVENT_EXTRA_DAY.getName())
//                .endRow()
//                .row()
//                .button("Дальше", Command.SAVE_EVENT_TIME.getName())
//                .endRow()
//                .build();
    }
}
