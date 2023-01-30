package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddDaySpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Comparator;
import java.util.List;

@PackagePrivate
@Component
public class InlineScheduleAddDaySpecificEventStartTime implements InlineHandler<Update> {
    final LessonScheduleService lessonScheduleService;
    final LessonService lessonService;
    final ChildService childService;
    final InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton;

    public InlineScheduleAddDaySpecificEventStartTime(LessonScheduleService lessonScheduleService,
                                                      LessonService lessonService, ChildService childService, InlineScheduleAddDaySpecificEventStartTimeButton inlineScheduleAddDaySpecificEventStartTimeButton) {
        this.lessonScheduleService = lessonScheduleService;
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineScheduleAddDaySpecificEventStartTimeButton = inlineScheduleAddDaySpecificEventStartTimeButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        String day = parseData(UpdateInfo.getData(update));
        String title = parseTitle(UpdateInfo.getText(update));
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        lessonSchedules.sort((lessonSchedule1, lessonSchedule2) -> comparator.compare(lessonSchedule1, lessonSchedule2));
        LessonSchedule lessonSchedule = lessonSchedules.get(lessonSchedules.size() - 1);
        lessonSchedule.setDay(day);
        lessonScheduleService.create(lessonSchedule);
        Child child = childService.findByUserId(childId);
        child.setState(State.NO_STATE.getState());
        child.setIsSkip(false);
        childService.create(child);
        ReplyKeyboard keyboard = inlineScheduleAddDaySpecificEventStartTimeButton.getKeyboard(lesson);
        String response = "День добавили для урока <i>\"" + lesson.getTitle() + "\"</i> , может чтото еще интересно?";
        String infoLesson = lessonService.getInfoLessonbyIdAndSendByUrl(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        return new MessageSenderImpl().sendMessageWithInline(childId, response, keyboard);
    }

    Comparator<LessonSchedule> comparator = new Comparator<LessonSchedule>() {
        @Override
        public int compare(LessonSchedule lessonSchedule1, LessonSchedule lessonSchedule2) {
            return lessonSchedule1.getLessonScheduleId().compareTo(lessonSchedule2.getLessonScheduleId());
        }
    };

    private String parseData(String data) {
        return data.split(Separator.COLON_SEPARATOR)[1];
    }

    private String parseTitle(String text) {
        return text.split(Separator.QUOTE_SEPARATOR)[1];
    }

}
