package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleDeleteSpecificEventStartTime2Button;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;

@FieldDefaults(level= AccessLevel.PRIVATE)
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
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = parseTitle(UpdateInfo.getData(update));
        LocalTime localTime = parseTime(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(localTime))
                .findFirst()
                .get();
        lessonScheduleService.delete(lessonSchedule);
        InlineKeyboardMarkup keyboard = specificEventStartTime2Button.getKeyboard();
        String response = "Можешь удалить еще один старт для урока <i>\"" + lesson.getTitle() + "\"</i>, только будь внимателен!";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, response);


    }

    private Long parseTitle(String data) {
        return Long.parseLong(data.split(Separator.COLON_SEPARATOR)[3]);
    }

    private LocalTime parseTime(String data) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = data.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }
}
