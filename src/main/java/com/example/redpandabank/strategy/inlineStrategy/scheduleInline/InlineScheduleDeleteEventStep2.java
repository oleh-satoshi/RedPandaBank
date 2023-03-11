package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import com.vdurmont.emoji.EmojiParser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteEventStep2 implements InlineHandler<Update> {
    final LessonScheduleService lessonScheduleService;
    final LessonService lessonService;
    final MessageSender messageSender;
    final TranslateService translateService;
    final String RESTORE = "restore";
    final String LESSON = "lesson";
    final String DELETE_LESSON_PART_2 = "delete-lesson-part-2";
    final String ALREADY_BEEN_REMOVED = "already-been-removed";
    final String I_WANT_TO_DELETE = "i-want-to-delete";
    final String BACK_TO_LESSONS = "back-to-lessons";
    final String MAKE_SURE_TO_DELETE_LESSON = "make-sure-to-delete-lesson";
    final String TEACHER = "teacher";
    final String START_AT = "start-at";
    final String WILL_END_IN = "will-end-in";
    final String DURATION = "duration";
    final String YES = "yes";
    private SendMessage sendMessage;
    Long childId;

    public InlineScheduleDeleteEventStep2(LessonScheduleService lessonScheduleService,
                                          LessonService lessonService,
                                          MessageSender messageSender, TranslateService translateService) {
        this.lessonScheduleService = lessonScheduleService;
        this.lessonService = lessonService;
        this.messageSender = messageSender;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        String title = UpdateInfo.getData(update);
        String content;
        Lesson lesson;
        String[] split = title.split(Separator.COLON_SEPARATOR);
        if (split[1].equals(YES)) {
            lesson = lessonService.getById(Long.valueOf(split[2]));
            if (!lesson.getIsDeleted()) {
                lesson.setIsDeleted(true);
                lessonService.create(lesson);
                InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkupBuilderImpl.create()
                        .row()
                        .button(translateService.getBySlug(RESTORE),
                                "/recoverData" + Separator.COLON_SEPARATOR + lesson.getLessonId())
                        .endRow()
                        .build();
                content = translateService.getBySlug(LESSON) + lesson.getTitle()
                        + translateService.getBySlug(DELETE_LESSON_PART_2);
                new MessageSenderImpl().sendMessageViaURL(childId,
                        new MessageSenderImpl().replaceSpace(content));
                return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId,
                        inlineKeyboardMarkup, "<strike>" + getLessonInfo(lesson) + "</strike>");
            } else {
                content = translateService.getBySlug(LESSON)
                        + lesson.getTitle() + translateService.getBySlug(ALREADY_BEEN_REMOVED);
                new MessageSenderImpl().sendMessageViaURL(childId,
                        new MessageSenderImpl().replaceSpace(content));
            }
        } else if (split[0].equals(Command.DELETE_EVENT_BY_ID.getName())) {
            lesson = lessonService.getById(Long.valueOf(split[1]));
            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkupBuilderImpl.create()
                    .row()
                    .button(translateService.getBySlug(I_WANT_TO_DELETE) + lesson.getTitle(),
                            Command.DELETE_EVENT_BY_ID.getName() + Separator.COLON_SEPARATOR
                                    + YES + Separator.COLON_SEPARATOR + lesson.getLessonId())
                    .endRow()
                    .row()
                    .button(translateService.getBySlug(BACK_TO_LESSONS),
                            Command.DELETE_EVENT.getName())
                    .endRow()
                    .build();
            content = translateService.getBySlug(MAKE_SURE_TO_DELETE_LESSON) + getLessonInfo(lesson);
            return new MessageSenderImpl()
                    .sendEditMessageWithInline(childId, messageId, inlineKeyboardMarkup, content);
        }
        return sendMessage;
    }

    private String getLessonInfo(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":school_satchel: " + "<b>" + lesson.getTitle() + "</b>" + "\n")
                .append(translateService.getBySlug(TEACHER) + "<i>" + lesson.getTeacher()
                        + "</i>" + "\n")
                .append(translateService.getBySlug(START_AT) + getStartTime(lesson))
                .append(translateService.getBySlug(WILL_END_IN) + getFinishTime(lesson) + "\n")
                .append(translateService.getBySlug(DURATION) + "<b>"
                        + lesson.getDuration() + "</b>"
                        + lessonService.getDuration(lesson.getDuration()) + "\n");

        return EmojiParser.parseToUnicode(stringBuilder.toString());
    }

    private String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>"
                        + lessonSchedule.getLessonStartTime() + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1) + "\n";
    }

    private String getFinishTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>"
                        + lessonSchedule.getLessonStartTime()
                        .plusMinutes(lesson.getDuration()) + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1);
    }
}
