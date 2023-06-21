package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleEditEventDayStep2;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
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
public class EditSpecificEventStartTimeStep2State implements StateHandler<Update> {
    Long userId;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleEditEventDayStep2 editEventDayStep2;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String START_TIME_CHANGED = "start-time-changed";

    public EditSpecificEventStartTimeStep2State(ChildService childService,
                                                LessonService lessonService,
                                                LessonScheduleService lessonScheduleService,
                                                InlineScheduleEditEventDayStep2 editEventDayStep2,
                                                TranslateService translateService,
                                                MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.editEventDayStep2 = editEventDayStep2;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        String title = parseTitleFromState(child.getState());
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        LessonSchedule lessonSchedule = setMinLocalTime(update, lesson);
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
        String response = translateService.getBySlug(START_TIME_CHANGED);
        InlineKeyboardMarkup keyboard = editEventDayStep2.getKeyboard(lesson, lessonSchedule);
        return messageSender.sendMessageWithReply(userId, response, keyboard);
    }

    private LessonSchedule setMinLocalTime(Update update, Lesson lesson) {

        LocalTime localTime = parseTime(UpdateInfo.getText(update));
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(LocalTime.MIN))
                .findFirst()
                .get();
        lessonSchedule.setLessonStartTime(localTime);
        lessonScheduleService.create(lessonSchedule);
        return lessonSchedule;
    }

    private String parseEventTitle(String name) {
        return name.split(Separator.QUOTE_SEPARATOR)[1];
    }

    private String parseTitleFromState(String name) {
        return name.split(Separator.COLON_SEPARATOR)[1];
    }

    private LocalTime parseTime(String text) {
        String[] response = text.split(Separator.COLON_SEPARATOR);
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
