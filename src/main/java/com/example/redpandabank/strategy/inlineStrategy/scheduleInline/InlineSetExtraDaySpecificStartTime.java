package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleAddDaySpecificEventStartTimeButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineSetExtraDaySpecificStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ChildService childService;
    final InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton;
    final TranslateService translateService;
    final String SOMETHING_ELSE_FOR_LESSON = "something-else-for-lesson";

    public InlineSetExtraDaySpecificStartTime(LessonService lessonService,
                                              LessonScheduleService lessonScheduleService,
                                              ChildService childService, InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton, TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.childService = childService;
        this.inlineScheduleAddDaySpecificEventStartTimeButton = inlineScheduleAddDaySpecificEventStartTimeButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseTitle(update.getCallbackQuery().getMessage().getText());
        String day = parseDay(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        LessonSchedule lessonSchedule = lessonSchedules.get(lessonSchedules.size() - 1);
        LessonSchedule newLessonSchedule = new LessonSchedule();
        newLessonSchedule.setDay(day);
        newLessonSchedule.setLessonStartTime(lessonSchedule.getLessonStartTime());
        lessonSchedules.add(newLessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonScheduleService.create(newLessonSchedule);
        lessonService.create(lesson);
        InlineKeyboardMarkup keyboard = inlineScheduleAddDaySpecificEventStartTimeButton.getKeyboard(lesson);
        String response = translateService.getBySlug(SOMETHING_ELSE_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i>?";
        String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, response);
    }

    private String parseTitle(String text) {
        return text.split(Separator.QUOTE_SEPARATOR)[1];
    }

    private String parseDay(String data) {
        return data.split(Separator.COLON_SEPARATOR)[1];
    }
}
