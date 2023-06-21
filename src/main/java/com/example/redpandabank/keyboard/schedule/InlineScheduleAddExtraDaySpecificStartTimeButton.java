package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.PressableWithArguments;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.util.Separator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineScheduleAddExtraDaySpecificStartTimeButton
        implements PressableWithArguments<InlineKeyboardMarkup, Lesson, LessonSchedule> {
    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson, LessonSchedule lessonSchedule) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.MONDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .extraButton(WeekDay.TUESDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.TUESDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .endRow()
                .row()
                .button(WeekDay.WEDNESDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.WEDNESDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .extraButton(WeekDay.THURSDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.THURSDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .endRow()
                .row()
                .button(WeekDay.FRIDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.FRIDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .extraButton(WeekDay.SATURDAY.getDay(),
                        Commands.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                                .substring(0, 15)
                                + Separator.COLON_SEPARATOR + WeekDay.SATURDAY.getDay()
                                + Separator.COLON_SEPARATOR + lesson.getId()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getDay()
                                + Separator.COLON_SEPARATOR + lessonSchedule.getLessonStartTime())
                .endRow()
                .build();
    }
}
