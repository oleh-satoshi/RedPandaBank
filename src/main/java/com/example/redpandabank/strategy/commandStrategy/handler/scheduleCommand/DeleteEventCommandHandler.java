package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.keyboard.main.BackToMainMenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class DeleteEventCommandHandler implements CommandHandler<Update> {
    final BackToMainMenuButton backToMainMenuButton;
    final LessonService lessonService;

    public DeleteEventCommandHandler(BackToMainMenuButton backToMainMenuButton,
                                     LessonService lessonService) {
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getMessage().getChatId();
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
