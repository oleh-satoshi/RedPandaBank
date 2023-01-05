package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InlineDeleteEvent implements InlineHandler<Update> {
    final LessonScheduleService lessonScheduleService;
    final LessonService lessonService;
    final MessageSender messageSender;
    final static String YES = "yes";
    final static String SEPARATOR = ":";
    Long childId;

    public InlineDeleteEvent(LessonScheduleService lessonScheduleService,
                             LessonService lessonService,
                             MessageSender messageSender) {
        this.lessonScheduleService = lessonScheduleService;
        this.lessonService = lessonService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        childId = update.getCallbackQuery().getMessage().getChatId();
        Lesson lesson;
        String title = update.getCallbackQuery().getData();
        String[] split = title.split(SEPARATOR);
        if (split[1].equals(YES)) {
            lesson = lessonService.getById(Long.valueOf(split[2]));
            List<LessonSchedule> allByLessonId = lessonScheduleService.findAllByLessonId(Long.valueOf(split[2]));
            for (LessonSchedule schedule : allByLessonId) {
                lessonScheduleService.delete(schedule);
            }
            lessonService.deleteById(lesson.getLessonId());
            messageSender.sendMessageToTelegram(childId, "<strike>" + getLessonInfo(lesson) + "</strike>");
            messageSender.sendMessageToTelegram(childId, "Урок" + lesson.getTitle() + " удален!");
        } else if (split[0].equals(Command.DELETE_EVENT_BY_ID.getName()) && split[1] != YES ) {
            lesson = lessonService.getById(Long.valueOf(split[1]));
            return InlineKeyboardMarkupBuilder.create(childId)
                    .setText("Ты точно хочешь удалить этот урок?\n\n" + getLessonInfo(lesson))
                    .row()
                    .button("Да, я хочу удалить " + lesson.getTitle(), Command.DELETE_EVENT_BY_ID.getName()
                            + SEPARATOR + YES + SEPARATOR + lesson.getLessonId())
                    .endRow()
                    .row()
                    .button("Нет, верника меня к списку уроков", Command.DELETE_EVENT.getName())
                    .endRow()
                    .build();
        }
        return null;
    }

    private String getLessonInfo(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":school_satchel: " + "<b>" + lesson.getTitle() + "</b>" + "\n")
                .append(":mortar_board: Учитель: " + "<i>" + lesson.getTeacher() + "</i>" + "\n")
                .append(":bell: " + "Начинается в " + getStartTime(lesson))
                .append(":checkered_flag: " + "Закончится в  " + getFinishTime(lesson) + "\n")
                .append(":clock8: " + "Идет " + "<b>" + lesson.getDuration() + "</b>" +
                        lessonService.getDuration(lesson.getDuration()) + "\n");

/*
        messageSender.sendMessageToTelegram(childId, EmojiParser.parseToUnicode(stringBuilder.toString()));
*/
        return EmojiParser.parseToUnicode(stringBuilder.toString());
    }

    private String getStartTime(Lesson lesson) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> stringList = lesson.getLessonSchedules().stream()
                    .map(lessonSchedule -> "<b>" + lessonSchedule.getLessonStartTime() + "</b>")
                    .collect(Collectors.toList());
            String string = stringBuilder.append(stringList).toString();
            return string.substring(1, string.length() - 1) + "\n";
        }

    private String getFinishTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>" + lessonSchedule.getLessonStartTime().plusMinutes(lesson.getDuration()) + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1);
    }
}

