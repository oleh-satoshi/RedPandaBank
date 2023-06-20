package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDurationButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SaveDurationEventState implements StateHandler<Update> {
    static final String NUMBERS_ONLY = "\\D";
    static final String PLUG = "";
    Long userId;
    String duration;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String DURATION_FOR_LESSON = "duration-for-lesson";
    final String LESSON_DURATION_INSTALLED_CHECK = "smth-saved-check";
    final String NEXT_BUTTON = "next";

    public SaveDurationEventState(ChildService childService, LessonService lessonService,
                                  InlineScheduleAddEventDurationButton
                                          inlineScheduleAddEventDurationButton,
                                  TranslateService translateService,
                                  MessageSender messageSender) {

        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineScheduleAddEventDurationButton = inlineScheduleAddEventDurationButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        duration = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        updateDurationForSpecificLesson();
        setNoStateForUser(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddEventDurationButton.getKeyboard();
        String response = getResponseBySlug();
        return messageSender.sendMessageWithInline(userId, response, keyboard);
    }

    private String getResponseBySlug() {
        return translateService.getBySlug(DURATION_FOR_LESSON)
                + translateService.getBySlug(LESSON_DURATION_INSTALLED_CHECK)
                + "<b>" + translateService.getBySlug(NEXT_BUTTON) + "</b>";
    }

    private void setNoStateForUser(Child child) {
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
    }

    private void updateDurationForSpecificLesson() {
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        lesson.setDuration(Integer.parseInt(duration.replaceAll(NUMBERS_ONLY, PLUG)));
        lesson.setId(lesson.getId());
        lessonService.create(lesson);
    }

}
