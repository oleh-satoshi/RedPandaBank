package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditSpecificEventStartTimeChooseOperationButton
        implements PressableWithArgument<ReplyKeyboard, Lesson> {
    final TranslateService translateService;
    final String CHANGE_TIME = "change-time";
    final String ADD_TIME = "add-time";

    public InlineScheduleEditSpecificEventStartTimeChooseOperationButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(CHANGE_TIME),
                        Command.EDIT_SCHEDULE_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button(translateService.getBySlug(ADD_TIME),
                        Command.ADD_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button(translateService.getBySlug(ADD_TIME),
                        Command.DELETE_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .build();
    }
}
