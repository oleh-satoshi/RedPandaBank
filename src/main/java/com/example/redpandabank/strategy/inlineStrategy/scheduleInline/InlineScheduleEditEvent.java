package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import java.util.ArrayList;
import java.util.List;

import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final String CHOOSE_LESSON_TO_CHANGE = "choose-lesson-to-change";
    final String BACK = "back";
    final MessageSender messageSender;

    public InlineScheduleEditEvent(LessonService lessonService,
                                   TranslateService translateService,
                                   MessageSender messageSender) {
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        List<Lesson> lessonsSet = lessonService.findAllByUserId(userId);
        List<Lesson> lessons = new ArrayList<>(lessonsSet);
        InlineKeyboardMarkup inline = getListOfLessonsInlineKeyboardMarkup(lessons);
        String response = translateService.getBySlug(CHOOSE_LESSON_TO_CHANGE);
        return messageSender.sendEditMessageWithInline(userId, messageId, inline, response);
    }

    private InlineKeyboardMarkup getListOfLessonsInlineKeyboardMarkup(List<Lesson> lessons) {
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (Lesson lesson : lessons) {
            builder.row();
            builder.button(lesson.getTitle(), Commands.EDIT_SPECIFIC_EXISTING_EVENT.getName()
                    + Separator.COLON_SEPARATOR + lesson.getId());
            builder.endRow();
        }
        InlineKeyboardMarkup inline = builder.row()
                .button(translateService.getBySlug(BACK),
                        Commands.EDIT_SCHEDULE.getName())
                .endRow()
                .build();
        return inline;
    }
}
