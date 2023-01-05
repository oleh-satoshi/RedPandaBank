
package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@PackagePrivate
@Component
public class InlineDeleteEventButton implements InlineHandler<Update> {
    final LessonService lessonService;

    public InlineDeleteEventButton(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        List<Lesson> allByTitle = lessonService.findAllByChildId(childId);

        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkupBuilder.create(childId)
                .setText("Внимательно посмотри на название урока и выбери тот который ты хочешь удалить..")
                .row();
        for (Lesson lesson : allByTitle) {
            inlineKeyboardMarkupBuilder.button(lesson.getTitle(), Command.DELETE_EVENT_BY_ID.getName() + ":" + lesson.getLessonId()).endRow();
        }
        return inlineKeyboardMarkupBuilder.build();
    }
}
