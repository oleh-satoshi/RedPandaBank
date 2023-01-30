package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;

@FieldDefaults(level= AccessLevel.PRIVATE)@Component
public class ScheduleDeleteEventCommandHandler implements CommandHandler<Update> {
    final static String SEPARATOR = ":";
    final LessonService lessonService;

    public ScheduleDeleteEventCommandHandler(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getMessage().getChatId();
        HashSet<Lesson> allByTitle = lessonService.findAllByChildId(childId);

        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilderImpl =
                InlineKeyboardMarkupBuilderImpl.create()
                .row();
        for (Lesson lesson : allByTitle) {
            inlineKeyboardMarkupBuilderImpl.button(lesson.getTitle(),
                    Command.DELETE_EVENT_BY_ID.getName() + SEPARATOR + lesson.getLessonId()).endRow();
        }
        SendMessage sendMessage = new MessageSenderImpl().sendMessageWithInline(childId,
                "Внимательно посмотри на название урока и выбери тот который ты хочешь удалить..",
                inlineKeyboardMarkupBuilderImpl.build());
        return sendMessage;
    }
}
