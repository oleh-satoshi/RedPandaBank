package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddTeacherNameButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.service.TelegramBot;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@PackagePrivate
@Component
public class SaveTeacherNameState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    String teacherName;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton;

    public SaveTeacherNameState(ChildService childService,
                                LessonService lessonService, InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineScheduleAddTeacherNameButton = inlineScheduleAddTeacherNameButton;
    }


    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        teacherName = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(teacherName, child)) {
            List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
            Lesson lesson = lessons.get(lessons.size() - 1);
            lesson.setTeacher(teacherName);
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            InlineKeyboardMarkup keyboard = inlineScheduleAddTeacherNameButton.getKeyboard(lesson);
            String response = "Учитель\"<i>" + lesson.getTeacher()
                    + "\"</i> добавлен! \n\nЕсли ты написал без ошибок то жми кнопку <b>Дальше</b>";
            return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }
}
