package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleAddExtraDaySpecificStartTimeButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
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
public class InlineScheduleAddExtraDaySpecificStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ChildService childService;
    final InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton;

    public InlineScheduleAddExtraDaySpecificStartTime(LessonService lessonService,
                                                      LessonScheduleService lessonScheduleService,
                                                      ChildService childService,
                                                      InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.childService = childService;
        this.inlineScheduleAddExtraDaySpecificStartTimeButton = inlineScheduleAddExtraDaySpecificStartTimeButton;
    }


    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseTitle(update.getCallbackQuery().getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        InlineKeyboardMarkup inline = inlineScheduleAddExtraDaySpecificStartTimeButton.getInline();
        String response = "Выбери в какой день урок <i>\"" + lesson.getTitle() + "\"</i> начинается в такое же время";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String parseTitle(String text) {
        return text.split(LessonService.QUOTE_SEPARATOR)[1];
    }
}
