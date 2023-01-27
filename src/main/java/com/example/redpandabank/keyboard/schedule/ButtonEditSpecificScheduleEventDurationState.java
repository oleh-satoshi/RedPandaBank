package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class ButtonEditSpecificScheduleEventDurationState {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Я ошибся, давай еще раз!", Command.EDIT_SCHEDULE_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button("Я закончил!", Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
