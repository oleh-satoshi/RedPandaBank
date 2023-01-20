package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineAddEventDuration {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Изменить длительность урока", Command.EDIT_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button("Дальше", Command.SAVE_EVENT_DAY.getName())
                .endRow()
                .build();
    }
}
