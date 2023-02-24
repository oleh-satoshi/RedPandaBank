package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleAddExtraDaySpecificStartTimeButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddExtraDaySpecificStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ChildService childService;
    final InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton;
    final TranslateService translateService;
    final String CHOOSE_DAY_FOR_LESSON = "choose-day-for-lesson";
    final String START_THE_SAME_TIME = "start-the-same-time";

    public InlineScheduleAddExtraDaySpecificStartTime(LessonService lessonService,
                                                      LessonScheduleService lessonScheduleService,
                                                      ChildService childService,
                                                      InlineScheduleAddExtraDaySpecificStartTimeButton inlineScheduleAddExtraDaySpecificStartTimeButton, TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.childService = childService;
        this.inlineScheduleAddExtraDaySpecificStartTimeButton = inlineScheduleAddExtraDaySpecificStartTimeButton;
        this.translateService = translateService;
    }


    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseTitle(update.getCallbackQuery().getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        InlineKeyboardMarkup keyboard = inlineScheduleAddExtraDaySpecificStartTimeButton.getKeyboard();
        String response = translateService.getBySlug(CHOOSE_DAY_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> "
        +translateService.getBySlug(START_THE_SAME_TIME);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, response);
    }

    private String parseTitle(String text) {
        return text.split(Separator.QUOTE_SEPARATOR)[1];
    }
}
