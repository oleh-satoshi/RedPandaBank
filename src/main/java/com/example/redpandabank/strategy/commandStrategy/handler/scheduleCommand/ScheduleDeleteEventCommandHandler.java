package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ScheduleDeleteEventCommandHandler implements CommandHandler<Update> {
    static final String SEPARATOR = ":";
    final LessonService lessonService;
    final TranslateService translateService;
    static final String CHOOSE_LESSON_TO_DELETE = "choose-lesson-to-delete";

    public ScheduleDeleteEventCommandHandler(LessonService lessonService,
                                             TranslateService translateService) {
        this.lessonService = lessonService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getMessage().getChatId();
        HashSet<Lesson> allByTitle = lessonService.getSetWithAllLessonByChildId(childId);

        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilderImpl =
                InlineKeyboardMarkupBuilderImpl.create()
                .row();
        for (Lesson lesson : allByTitle) {
            inlineKeyboardMarkupBuilderImpl.button(lesson.getTitle(),
                    Command.DELETE_EVENT_BY_ID.getName()
                            + SEPARATOR + lesson.getLessonId()).endRow();
        }
        SendMessage sendMessage = new MessageSenderImpl().sendMessageWithInline(childId,
                translateService.getBySlug(CHOOSE_LESSON_TO_DELETE),
                inlineKeyboardMarkupBuilderImpl.build());
        return sendMessage;
    }
}
