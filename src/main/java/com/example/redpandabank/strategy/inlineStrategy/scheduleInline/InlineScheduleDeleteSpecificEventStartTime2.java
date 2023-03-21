package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleDeleteSpecificEventStartTime2Button;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteSpecificEventStartTime2 implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleDeleteSpecificEventStartTime2Button specificEventStartTime2Button;
    final TranslateService translateService;
    final String REMOVE_ANOTHER_LESSON_START = "remove-another-lesson-start";
    final String JUST_BE_CAREFUL = "just-be-careful";

    public InlineScheduleDeleteSpecificEventStartTime2(LessonService lessonService,
                                                       LessonScheduleService lessonScheduleService,
                                                       InlineScheduleDeleteSpecificEventStartTime2Button
                                                               specificEventStartTime2Button,
                                                       TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.specificEventStartTime2Button = specificEventStartTime2Button;
        this.translateService = translateService;
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
        String response = translateService.getBySlug(REMOVE_ANOTHER_LESSON_START)
                + " <i>\"" + lesson.getTitle() + "\"</i>, "
                + translateService.getBySlug(JUST_BE_CAREFUL);
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
