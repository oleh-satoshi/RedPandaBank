package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineEditSpecificEventStartTimeChooseOperationButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@PackagePrivate
@Component
public class InlineEditSpecificEventStartTimeChooseOperation implements InlineHandler<Update> {
    final InlineEditSpecificEventStartTimeChooseOperationButton chooseOperationButton;
    final LessonService lessonService;


    public InlineEditSpecificEventStartTimeChooseOperation(InlineEditSpecificEventStartTimeChooseOperationButton chooseOperationButton,
                                                           LessonService lessonService) {
        this.chooseOperationButton = chooseOperationButton;
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseTitle(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        String response = "Что ты хочешь сделать со временем начала урока <i>\"" + lesson.getTitle() + "\"</i> !";
        InlineKeyboardMarkup inline = chooseOperationButton.getInline(lesson);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String parseTitle(String command) {
        return command.split(LessonService.COLON_SEPARATOR)[1];
    }
}