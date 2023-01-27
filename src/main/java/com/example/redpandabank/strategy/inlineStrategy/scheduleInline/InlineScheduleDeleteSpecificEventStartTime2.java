package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleDeleteSpecificEventStartTime2Button;
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
public class InlineScheduleDeleteSpecificEventStartTime2 implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button;

    public InlineScheduleDeleteSpecificEventStartTime2(LessonService lessonService, LessonScheduleService lessonScheduleService,
                                                       InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Long lessonId = parseTitle(update.getCallbackQuery().getData());
        LocalTime localTime = parseTime(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.getById(lessonId);
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(localTime))
                .findFirst()
                .get();
        lessonScheduleService.delete(lessonSchedule);
        //Lesson lessonById = lessonService.getById(lessonId);
//        String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
//        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        InlineKeyboardMarkup inline = specificEventStartTime2Button.getInline();
        String response = "Можешь удалить еще один старт для урока <i>\"" + lesson.getTitle() + "\"</i>, только будь внимателен!";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);


    }

    private Long parseTitle(String data) {
        return Long.parseLong(data.split(LessonService.COLON_SEPARATOR)[3]);
    }

    private LocalTime parseTime(String data) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = data.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }
}
