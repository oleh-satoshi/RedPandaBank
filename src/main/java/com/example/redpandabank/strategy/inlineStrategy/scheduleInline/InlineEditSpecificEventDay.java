package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.WeekDay;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineEditSpecificEventDay implements InlineHandler<Update>  {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final MessageSender messageSender;
    final TranslateService translateService;
    final String START_TIME_AND_DAY_UPDATED = "start-time-day-updated";

    public InlineEditSpecificEventDay(LessonService lessonService,
                                      LessonScheduleService lessonScheduleService,
                                      MessageSender messageSender,
                                      TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.messageSender = messageSender;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = getLessonId(UpdateInfo.getData(update));
        LocalTime startTime = getStartTime(UpdateInfo.getData(update));
        String oldDay = getOldDay(UpdateInfo.getData(update));
        String newDay = getNewDay(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        LessonSchedule lessonSchedule = setLessonScheduleDay(startTime, oldDay, newDay, lesson);
        String lessonInfo =
                lessonService.getLessonInfoByLessonAndLessonScheduleForSendByUrl(lesson, lessonSchedule);
        messageSender.sendMessageViaURL(userId, lessonInfo);
        String response = translateService.getBySlug(START_TIME_AND_DAY_UPDATED);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private LessonSchedule setLessonScheduleDay(LocalTime startTime, String oldDay, String newDay, Lesson lesson) {
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonschedule -> lessonschedule.getLessonStartTime().equals(startTime))
                .filter(lessonschedule -> lessonschedule.getDay().startsWith(oldDay))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can't find any lessonSchedules by day "
                                + "and specific start time"));
                        new RuntimeException("Can't find any lessonSchedules by day " +
                                "and specific start time"));
      lessonSchedule.setDay(newDay);
        return lessonScheduleService.create(lessonSchedule);
    }

    private String getNewDay(String data) {
        WeekDay[] days = WeekDay.values();
        for (int i = 0; i < days.length; i++) {
            if (data.split(Separator.COLON_SEPARATOR)[0]
                    .contains(days[i].getDay())) {
                return days[i].getDay();
            }
        }
        return data;
    }

    private String getOldDay(String data) {
        return data.split(Separator.COLON_SEPARATOR)[4];
    }

    private LocalTime getStartTime(String data) {
        String hours = data.split(Separator.COLON_SEPARATOR)[2];
        String minutes = data.split(Separator.COLON_SEPARATOR)[3];
        return LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes));

    }

    private Long getLessonId(String data) {
        return Long.parseLong(data.split(Separator.COLON_SEPARATOR)[1]);
    }
}
