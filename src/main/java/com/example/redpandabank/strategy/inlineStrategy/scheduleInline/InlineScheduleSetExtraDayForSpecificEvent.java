package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeAndDayButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
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
public class InlineScheduleSetExtraDayForSpecificEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton;
    final TranslateService translateService;

    public InlineScheduleSetExtraDayForSpecificEvent(LessonService lessonService,
                                                     LessonScheduleService lessonScheduleService,
                                                     MessageSender messageSender,
                                                     InlineScheduleAddEventTimeAndDayButton
                                                             inlineScheduleAddEventTimeAndDayButton,
                                                     TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.inlineScheduleAddEventTimeAndDayButton = inlineScheduleAddEventTimeAndDayButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = parseLessonId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        String oldDay = parseOldDay(UpdateInfo.getData(update));
        String newDay = parseNewDay(UpdateInfo.getData(update));
        LocalTime startTime = parseStartTime(UpdateInfo.getData(update));
        LessonSchedule lessonSchedule = setNewDayToLessonSchedule(lesson, oldDay, newDay, startTime);
        lessonService.create(lesson);
        String lessonInfo =
                lessonService.getLessonInfoByLessonAndLessonScheduleForSendByUrl(lesson, lessonSchedule);
        messageSender.sendMessageViaURL(userId, lessonInfo);
        String content = translateService.getBySlug("start-time-changed");
        InlineKeyboardMarkup keyboard =
                inlineScheduleAddEventTimeAndDayButton.getKeyboard();
        return messageSender.sendEditMessageWithInline(userId, messageId, keyboard, content);
    }

    private LessonSchedule setNewDayToLessonSchedule(Lesson lesson, String oldDay, String newDay, LocalTime startTime) {
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonschedule -> lessonschedule.getLessonStartTime().equals(startTime))
                .filter(lessonschedule -> lessonschedule.getDay().equals(oldDay))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find lessonSchedule by parameters"));
        lessonSchedule.setDay(newDay);
        return lessonScheduleService.create(lessonSchedule);
    }

    private LocalTime parseStartTime(String data) {
        String[] dataArray = data.split(Separator.COLON_SEPARATOR);
        return LocalTime.of(Integer.parseInt(dataArray[4]),
                Integer.parseInt(dataArray[5]));
    }

    private String parseNewDay(String data) {
        return data.split(Separator.COLON_SEPARATOR)[1];
    }

    private String parseOldDay(String data) {
        return data.split(Separator.COLON_SEPARATOR)[3];
    }

    private Long parseLessonId(String data) {
        return Long.parseLong(data.split(Separator.COLON_SEPARATOR)[2]);
    }
}
