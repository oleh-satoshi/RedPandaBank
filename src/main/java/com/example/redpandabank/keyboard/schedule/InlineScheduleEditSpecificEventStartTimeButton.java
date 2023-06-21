package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
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
public class InlineScheduleEditSpecificEventStartTimeButton
        implements PressableWithArgument<ReplyKeyboard, Lesson> {
    final TranslateService translateService;
    final String CHANGE_TIME = "change-time-lesson";
    final String ADD_TIME = "add-time-lesson";
    final String DELETE_TIME = "delete-time-lesson";
    final String BACK = "back";

    public InlineScheduleEditSpecificEventStartTimeButton(
            TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(CHANGE_TIME),
                        Commands.EDIT_SCHEDULE_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button(translateService.getBySlug(ADD_TIME),
                        Commands.SET_ADD_SPECIFIC_EVENT_START_TIME_STATE.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button(translateService.getBySlug(DELETE_TIME),
                        Commands.DELETE_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK),
                        Commands.EDIT_SPECIFIC_EXISTING_EVENT.getName()
                                + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .build();
    }
}
