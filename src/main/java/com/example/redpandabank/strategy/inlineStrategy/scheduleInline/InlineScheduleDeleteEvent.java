package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String CHOOSE_LESSON_TO_DELETE = "choose-lesson-to-delete";
    final String BACK = "back";

    public InlineScheduleDeleteEvent(LessonService lessonService,
                                     TranslateService translateService,
                                     MessageSender messageSender) {
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        HashSet<Lesson> allByTitle = lessonService.getSetWithAllLessonByChildId(childId);
        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkup =
                InlineKeyboardMarkupBuilderImpl.create()
                .row();
        for (Lesson lesson : allByTitle) {
            inlineKeyboardMarkup.button(lesson.getTitle(),
                    Commands.DELETE_EVENT_BY_ID.getName()
                            + ":" + lesson.getId()).endRow();
        }
        InlineKeyboardMarkup inline = inlineKeyboardMarkup.row()
                .button(translateService.getBySlug(BACK),
                        Commands.EDIT_SCHEDULE.getName())
                .endRow()
                .build();
        String content = translateService.getBySlug(CHOOSE_LESSON_TO_DELETE);
        return messageSender.sendEditMessageWithInline(childId, messageId, inline, content);
    }
}
