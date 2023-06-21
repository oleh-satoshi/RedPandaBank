package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddAgainEventTimeAndDayButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeAndDayButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AddSpecificEventStartTimeState implements StateHandler<Update> {
    Long userId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final TranslateService translateService;
    final MessageSender messageSender;
    static final String TIME_PATTERN = ".*?(\\d{1,2}:\\d{2}).*";
    final InlineScheduleAddAgainEventTimeAndDayButton
            inlineScheduleAddAgainEventTimeAndDayButton;
    final String RESCHEDULED_START_TIME_AND_DAY = "rescheduled-start-time-and-day";
    final String WRONG_TIME_FORMAT = "wrong-time-format";


    public AddSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                          LessonScheduleService lessonScheduleService,
                                          TranslateService translateService,
                                          MessageSender messageSender,
                                          InlineScheduleAddAgainEventTimeAndDayButton
                                                  inlineScheduleAddAgainEventTimeAndDayButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.translateService = translateService;
        this.messageSender = messageSender;
        this.inlineScheduleAddAgainEventTimeAndDayButton = inlineScheduleAddAgainEventTimeAndDayButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        Child child = childService.findByUserId(userId);
        LocalTime localTime = parseTime(UpdateInfo.getText(update), userId);
        if (localTime != null) {
            String title = parseTitleFromState(child.getState());
            Lesson lesson = lessonService.findLessonByTitle(userId, title);
            setStartTimeForLessonSchedule(lesson, localTime);
            setNoStateAndIsSkipForUser(child);
            String response = getResponse();
            InlineKeyboardMarkup keyboard =
                    inlineScheduleAddAgainEventTimeAndDayButton.getKeyboard();
            return messageSender.sendMessageWithInline(userId, response, keyboard);
        }
        return null;
    }

    private LessonSchedule setStartTimeForLessonSchedule(Lesson lesson, LocalTime localTime) {
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        LessonSchedule lessonSchedule = lessonSchedules.get(lessonSchedules.size() - 1);
        lessonSchedule.setLessonStartTime(localTime);
        lessonSchedules.add(lessonSchedule);
        lessonScheduleService.create(lessonSchedule);
        lessonService.create(lesson);
        return lessonSchedule;
    }

    private void setNoStateAndIsSkipForUser(Child child) {
        child.setState(StateCommands.NO_STATE.getState());
        child.setIsSkip(false);
        childService.create(child);
    }

    private String getResponse() {
        return translateService.getBySlug(RESCHEDULED_START_TIME_AND_DAY);
    }

    private LocalTime parseTime(String text, Long userId) {
        try {
            String[] response = text.replaceAll(TIME_PATTERN, "$1")
                    .split(":");
            return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
        } catch (Exception e) {
            String content = messageSender.replaceSpace(translateService.getBySlug(WRONG_TIME_FORMAT));
            messageSender.sendMessageViaURL(userId, content);
            return null;
        }
    }

    private String parseTitleFromState(String title) {
        return title.split(Separator.COLON_SEPARATOR)[1];
    }

}
