package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class EditSpecificEventTeacherNameState implements StateHandler<Update> {
    Long userId;
    String teacherName;
    final ChildService childService;
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String TEACHER_CHANGED = "teacher-changed";

    public EditSpecificEventTeacherNameState(ChildService childService,
                                             LessonService lessonService,
                                             ReplyMainMenuButton mainMenuButton,
                                             TranslateService translateService,
                                             MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        userId = UpdateInfo.getUserId(update);
        teacherName = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        Lesson lesson = setNewTeacherName(child);
        setNoStateForUser(child);
        String response = translateService.getBySlug(TEACHER_CHANGED);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        String infoLesson = lessonService.getLessonInfoByIdForSendByUrl(lesson.getId());
        messageSender.sendMessageViaURL(userId, infoLesson);
        return messageSender.sendMessageWithReply(userId, response, keyboard);
    }

    private void setNoStateForUser(Child child) {
        child.setState(StateCommands.NO_STATE.getState());
        childService.create(child);
    }

    private Lesson setNewTeacherName(Child child) {
        Long lessonId = parseId(child.getState());
        Lesson lesson = lessonService.getById(lessonId);
        lesson.setTeacher(teacherName);
        lessonService.create(lesson);
        return lesson;
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
