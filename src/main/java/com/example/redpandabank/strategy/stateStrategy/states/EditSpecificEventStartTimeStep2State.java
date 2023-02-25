package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.time.LocalTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class EditSpecificEventStartTimeStep2State implements StateHandler<Update> {
    Long userId;
    String lessonTitle;
    Integer messageId;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final ReplyMainMenuButton mainMenuButton;
    final TranslateService translateService;
    final String START_TIME_CHANGED = "start-time-changed";

    public EditSpecificEventStartTimeStep2State(ChildService childService, LessonService lessonService,
                                                LessonScheduleService lessonScheduleService,
                                                ReplyMainMenuButton mainMenuButton,
                                                TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.mainMenuButton = mainMenuButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        messageId = UpdateInfo.getMessageId(update);
        lessonTitle = UpdateInfo.hasReply(update) ? UpdateInfo.getText(update) : parseEventTitle(
                UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        String title = parseTitleFromState(child.getState());
        LocalTime localTime = parseTime(update.getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        LessonSchedule lessonSchedule = lesson.getLessonSchedules().stream()
                .filter(lessonSchedul -> lessonSchedul.getLessonStartTime().equals(LocalTime.MIN))
                .findFirst()
                .get();
        lessonSchedule.setLessonStartTime(localTime);
        lessonScheduleService.create(lessonSchedule);
        child.setState(State.NO_STATE.getState());
        childService.create(child);
        String response = translateService.getBySlug(START_TIME_CHANGED);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        String infoLesson = lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
        return new MessageSenderImpl().sendMessageWithReply(userId, response, keyboard);
    }

    private String parseEventTitle(String name) {
        return name.split(Separator.QUOTE_SEPARATOR)[1];
    }

    private String parseTitleFromState(String name) {
        return name.split(Separator.COLON_SEPARATOR)[1];
    }

    private LocalTime parseTime(String text) {
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}
