package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddTeacherNameButton;
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
public class SaveTeacherNameState implements StateHandler<Update> {
    Long userId;
    String teacherName;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String TEACHER = "teacher";
    final String SMTH_SAVED_CHECK = "smth-saved-check";
    final String NEXT_BUTTON = "next";

    public SaveTeacherNameState(ChildService childService,
                                LessonService lessonService,
                                InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton,
                                TranslateService translateService,
                                MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineScheduleAddTeacherNameButton = inlineScheduleAddTeacherNameButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        teacherName = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        setTeacherNameForSpecificLesson(lesson);
        setStateFofChild(child);
        InlineKeyboardMarkup keyboard = inlineScheduleAddTeacherNameButton.getKeyboard(lesson);
        String response = getStringBySlug(lesson);
        return messageSender.sendMessageWithInline(userId, response, keyboard);
    }

    private void setTeacherNameForSpecificLesson(Lesson lesson) {
        lesson.setTeacher(teacherName);
        lessonService.create(lesson);
    }

    private void setStateFofChild(Child child) {
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
    }

    private String getStringBySlug(Lesson lesson) {
        return translateService.getBySlug(TEACHER)
                + " \"<i>" + lesson.getTeacher() + "\"</i> "
                + translateService.getBySlug(SMTH_SAVED_CHECK)
                + "<b>" + translateService.getBySlug(NEXT_BUTTON) + "</b>";
    }
}
