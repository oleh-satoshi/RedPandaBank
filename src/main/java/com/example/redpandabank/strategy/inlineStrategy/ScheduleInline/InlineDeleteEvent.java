package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InlineDeleteEvent implements InlineHandler<Update> {
    final LessonScheduleService lessonScheduleService;
    final LessonService lessonService;
    final MessageSender messageSender;
    final static String YES = "yes";
    final static String SEPARATOR = ":";
    private SendMessage sendMessage;
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
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Lesson lesson;
        String title = update.getCallbackQuery().getData();
        String[] split = title.split(SEPARATOR);
        if (split[1].equals(YES)) {
            lesson = lessonService.getById(Long.valueOf(split[2]));
            if (!lesson.getIsDeleted()) {
                lesson.setIsDeleted(true);
                lessonService.create(lesson);
                InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkupBuilderImpl.create()
                        .row()
                        .button("Восстановить", "/recoverData" + SEPARATOR + lesson.getLessonId())
                        .endRow()
                        .build();
                new MessageSenderImpl().sendMessageViaURL(childId, "Урок " + lesson.getTitle() + " удален! Если ты ошибся и удалил не тот урок ты можешь нажать кнопочку Восстановить и я верну урок обратно");
                return new MessageSenderImpl().sendMessageViaEditMessageTextWithInline(childId, messageId,
                        inlineKeyboardMarkup, "<strike>" + getLessonInfo(lesson) + "</strike>");
            } else {
                new MessageSenderImpl().sendMessageViaURL(childId, "Урок " + lesson.getTitle() + " уже был удален!");
            }
        } else if (split[0].equals(Command.DELETE_EVENT_BY_ID.getName())) {
            lesson = lessonService.getById(Long.valueOf(split[1]));
            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkupBuilderImpl.create()
                    .row()
                    .button("Да, я хочу удалить " + lesson.getTitle(),
                            Command.DELETE_EVENT_BY_ID.getName() + SEPARATOR + YES + SEPARATOR + lesson.getLessonId())
                    .endRow()
                    .row()
                    .button("Нет, верника меня к списку уроков", Command.DELETE_EVENT.getName())
                    .endRow()
                    .build();
            String content = "Ты точно хочешь удалить этот урок?\n\n" + getLessonInfo(lesson);
            return new MessageSenderImpl().sendMessageViaEditMessageTextWithInline(childId, messageId, inlineKeyboardMarkup, content);
//            return new MessageSenderImpl().sendMessageWithInline(childId,
//                    "Ты точно хочешь удалить этот урок?\n\n" + getLessonInfo(lesson),
//                            inlineKeyboardMarkup);
        }
        return sendMessage;
    }

    private String getLessonInfo(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":school_satchel: " + "<b>" + lesson.getTitle() + "</b>" + "\n")
                .append(":mortar_board: Учитель: " + "<i>" + lesson.getTeacher() + "</i>" + "\n")
                .append(":bell: " + "Начинается в " + getStartTime(lesson))
                .append(":checkered_flag: " + "Закончится в  " + getFinishTime(lesson) + "\n")
                .append(":clock8: " + "Идет " + "<b>" + lesson.getDuration() + "</b>" +
                        lessonService.getDuration(lesson.getDuration()) + "\n");

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

