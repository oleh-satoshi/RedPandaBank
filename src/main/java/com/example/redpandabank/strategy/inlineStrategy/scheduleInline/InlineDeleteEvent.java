
package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashSet;

@PackagePrivate
@Component
public class InlineDeleteEvent implements InlineHandler<Update> {
    final LessonService lessonService;

    public InlineDeleteEvent(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        HashSet<Lesson> allByTitle = lessonService.findAllByChildId(childId);

        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkup = InlineKeyboardMarkupBuilderImpl.create()
                .row();
        for (Lesson lesson : allByTitle) {
            inlineKeyboardMarkup.button(lesson.getTitle(), Command.DELETE_EVENT_BY_ID.getName() + ":" + lesson.getLessonId()).endRow();
        }
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboardMarkup.build();
        String content = "Внимательно посмотри на название урока и выбери тот который ты хочешь удалить..";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboardMarkup, content);
    }
}
