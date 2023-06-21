package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.PressableWithArguments;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventDayStep2
        implements PressableWithArguments<InlineKeyboardMarkup, Lesson, LessonSchedule> {

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson, LessonSchedule lessonSchedule) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.MONDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .extraButton(WeekDay.TUESDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.TUESDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .endRow()
                .row()
                .button(WeekDay.WEDNESDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.WEDNESDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .extraButton(WeekDay.THURSDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.THURSDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .endRow()
                .row()
                .button(WeekDay.FRIDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.FRIDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .extraButton(WeekDay.SATURDAY.getDay(),
                        Commands.EDIT_EVENT_DAY.getName()
                                + WeekDay.SATURDAY.getDay()
                                + Separator.COLON_SEPARATOR
                                + lesson.getId()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getLessonStartTime()
                                + Separator.COLON_SEPARATOR
                                + lessonSchedule.getDay().substring(0, 3))
                .endRow()
                .build();
    }
}
