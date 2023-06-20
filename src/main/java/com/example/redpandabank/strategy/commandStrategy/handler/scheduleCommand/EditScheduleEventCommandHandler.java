package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import static com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.ScheduleDeleteEventCommandHandler.SEPARATOR;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
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
public class EditScheduleEventCommandHandler implements CommandHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final String WHAT_LESSON_EDIT = "what-lesson-edit";

    public EditScheduleEventCommandHandler(LessonService lessonService,
                                           TranslateService translateService) {
        this.lessonService = lessonService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getMessage().getChatId();
        HashSet<Lesson> lessons = lessonService.getSetWithAllLessonByChildId(childId);

        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilderImpl =
                InlineKeyboardMarkupBuilderImpl.create()
                        .row();
        for (Lesson lesson : lessons) {
            inlineKeyboardMarkupBuilderImpl.button(lesson.getTitle(),
                    StateCommands.EDIT_SPECIFIC_EVENT_FIELD.getState()
                            + SEPARATOR + lesson.getTitle()).endRow();
        }
        SendMessage sendMessage = new MessageSenderImpl().sendMessageWithInline(childId,
                translateService.getBySlug(WHAT_LESSON_EDIT),
                inlineKeyboardMarkupBuilderImpl.build());
        return sendMessage;
    }
}
