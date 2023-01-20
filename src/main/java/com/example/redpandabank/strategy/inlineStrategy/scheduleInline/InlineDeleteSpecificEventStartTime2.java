package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineDeleteSpecificEventStartTime2Button;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;

@PackagePrivate
@Component
public class InlineDeleteSpecificEventStartTime2 implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineDeleteSpecificEventStartTime2Button specificEventStartTime2Button;

    public InlineDeleteSpecificEventStartTime2(LessonService lessonService, LessonScheduleService lessonScheduleService,
                                               InlineDeleteSpecificEventStartTime2Button specificEventStartTime2Button) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseTitle(update.getCallbackQuery().getData());
        LocalTime localTime = parseTime(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(localTime))
                .findFirst()
                .get();
        lessonScheduleService.delete(lessonSchedule);
        Lesson lessonByTitle = lessonService.findLessonByTitle(childId, title);
//        String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
//        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        InlineKeyboardMarkup inline = specificEventStartTime2Button.getInline();
        String response = "Можешь удалить еще один старт для урока <i>\"" + lesson.getTitle() + "\"</i>, только будь внимателен!";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);


    }

    private String parseTitle(String data) {
        return data.split(LessonService.COLON_SEPARATOR)[3];
    }

    private LocalTime parseTime(String data) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = data.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }
}
