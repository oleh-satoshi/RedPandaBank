package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final String CHOOSE_LESSON_TO_CHANGE = "choose-lesson-to-change";
    final String BACK = "back";

    public InlineScheduleEditEvent(LessonService lessonService,
                                   TranslateService translateService) {
        this.lessonService = lessonService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        List<Lesson> lessonsSet = lessonService.findAllByChildIdWithoutLessonSchedule(childId);
        List<Lesson> lessons = new ArrayList<>(lessonsSet);
        String lessonTitle;
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (Lesson lesson : lessons) {
            builder.row();
            builder.button(lesson.getTitle(), Command.EDIT_SPECIFIC_EXISTING_EVENT.getName()
                    + Separator.COLON_SEPARATOR + lesson.getLessonId());
            builder.endRow();
        }
        InlineKeyboardMarkup inline = builder.row()
                .button(translateService.getBySlug(BACK),
                        Command.EDIT_SCHEDULE.getName())
                .endRow()
                .build();
        String response = translateService.getBySlug(CHOOSE_LESSON_TO_CHANGE);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }
}
