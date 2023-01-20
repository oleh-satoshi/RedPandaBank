package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineAddDaySpecificEventStartTimeButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
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

import java.util.List;

@PackagePrivate
@Component
public class InlineSetExtraDaySpecificStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ChildService childService;
    final InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton;
    public InlineSetExtraDaySpecificStartTime(LessonService lessonService,
                                              LessonScheduleService lessonScheduleService,
                                              ChildService childService, InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.childService = childService;
        this.inlineAddDaySpecificEventStartTimeButton = inlineAddDaySpecificEventStartTimeButton;
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
        newLessonSchedule.setChildId(childId);
        newLessonSchedule.setDay(day);
        newLessonSchedule.setLessonStartTime(lessonSchedule.getLessonStartTime());
        lessonSchedules.add(newLessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonScheduleService.create(newLessonSchedule);
        lessonService.create(lesson);
        InlineKeyboardMarkup inline = inlineAddDaySpecificEventStartTimeButton.getInline(lesson);
        String response = "Может что то еще интересно для урока <i>\"" + lesson.getTitle() + "\"</i>?";
        String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String parseTitle(String text) {
        return text.split(LessonService.QUOTE_SEPARATOR)[1];
    }

    private String parseDay(String data) {
        return data.split(LessonService.COLON_SEPARATOR)[1];
    }
}
