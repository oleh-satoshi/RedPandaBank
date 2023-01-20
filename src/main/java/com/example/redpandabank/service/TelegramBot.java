package com.example.redpandabank.service;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineStrategy;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.LocalTime;
import java.util.*;

@PackagePrivate
@Component
public class TelegramBot {
    Set<String> hashSet;
    Set generalCommands;
    final CommandStrategy commandStrategy;
    final InlineStrategy inlineStrategy;
    final ChildService childService;
    final LessonService lessonService;
    final InlineRepeatAddLesson inlineRepeatAddLesson;
    final InlineCheckCorrectTitle inlineCheckCorrectTitle;
    final InlineAddTeacherName inlineAddTeacherName;
    final InlineAddEventDuration inlineAddEventDuration;
    final InlineAddEventDay inlineAddEventDay;
    final LessonScheduleService lessonScheduleService;
    final InlineAddExtraDay inlineAddExtraDay;
    final InlineAddEvenTime inlineAddEvenTime;
    final ReplyMainMenuButton replyMainMenuButton;
    final InlineAddSpecificEventStartTimeButton addSpecificEventStartTimeButton;

    public TelegramBot(CommandStrategy commandStrategy,
                       InlineStrategy inlineStrategy,
                       ChildService childService, LessonService lessonService,
                       InlineRepeatAddLesson inlineRepeatAddLesson,
                       InlineCheckCorrectTitle inlineCheckCorrectTitle,
                       InlineAddTeacherName inlineAddTeacherName,
                       InlineAddEventDuration inlineAddEventDuration,
                       InlineAddEventDay inlineAddEventDay,
                       LessonScheduleService lessonScheduleService,
                       InlineAddExtraDay inlineAddExtraDay,
                       InlineAddEvenTime inlineAddEvenTime, ReplyMainMenuButton replyMainMenuButton,
                       InlineAddSpecificEventStartTimeButton addSpecificEventStartTimeButton) {
        this.commandStrategy = commandStrategy;
        this.inlineStrategy = inlineStrategy;
        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineRepeatAddLesson = inlineRepeatAddLesson;
        this.inlineCheckCorrectTitle = inlineCheckCorrectTitle;
        this.inlineAddTeacherName = inlineAddTeacherName;
        this.inlineAddEventDuration = inlineAddEventDuration;
        this.inlineAddEventDay = inlineAddEventDay;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineAddExtraDay = inlineAddExtraDay;
        this.inlineAddEvenTime = inlineAddEvenTime;
        this.replyMainMenuButton = replyMainMenuButton;
        this.addSpecificEventStartTimeButton = addSpecificEventStartTimeButton;

        generalCommands = new HashSet();
        for (int i = 0; i < Command.values().length; i++) {
            generalCommands.add(Command.values()[i].getName());
        }
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Boolean hasReply = update.hasMessage() && update.getMessage().hasText();
        Boolean hasCallback = update.hasCallbackQuery();
        String replyCommand;
            if (hasReply || hasCallback) {
            Long userId = hasReply ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
            replyCommand = hasReply ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
            Child child = childService.getById(userId);

            if (child.getState().equals(State.SAVE_TITLE_EVENT.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                replyCommand = hasReply ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
                if (lessonService.findAllByTitle(replyCommand, userId)) {
                    Lesson lesson = new Lesson();
                    lesson.setChildId(userId);
                    lesson.setTitle(replyCommand);
                    lesson.setIsDeleted(false);
                    lessonService.create(lesson);
                    child.setState(State.NO_STATE.getState());
                    childService.create(child);
                    InlineKeyboardMarkup inline = inlineCheckCorrectTitle.getInline(lesson);
                    String response = "Урок \"<i>" + lesson.getTitle()
                            + "\"</i> сохранили!\n\nЕсли ты написал без ошибок то жми кнопку <b>Дальше</b>";
                    return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
                } else {
                    child.setState(State.NO_STATE.getState());
                    childService.create(child);
                    InlineKeyboardMarkup inline = inlineRepeatAddLesson.getInline();
                    return new MessageSenderImpl().sendMessageWithInline(userId,
                            "Такой урок уже сохраняли!", inline);
                }
            } else if (child.getState().equals(State.SAVE_TEACHER_NAME.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
                Lesson lesson = lessons.get(lessons.size() - 1);
                lesson.setTeacher(replyCommand);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                InlineKeyboardMarkup inline = inlineAddTeacherName.getInline(lesson);
                String response = "Учитель\"<i>" + lesson.getTeacher()
                        + "\"</i> добавлен! \n\nЕсли ты написал без ошибок то жми кнопку <b>Дальше</b>";
                return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
            } else if (child.getState().equals(State.SAVE_DURATION.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                replyCommand = hasReply ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
                List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
                Lesson lesson = lessons.get(lessons.size() - 1);
                lesson.setDuration(Integer.parseInt(replyCommand.replaceAll("\\W", "")));
                lesson.setLessonId(lesson.getLessonId());
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                InlineKeyboardMarkup inline = inlineAddEventDuration.getInline();
                String response = "Длительность для урока \"<i>" + lesson.getTitle()
                        + "\"</i> установили!\n\nЕсли ты написал без ошибок то жми кнопку <b>Дальше</b>";
                return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
            } else if (child.getState().equals(State.SAVE_EVENT_DAY.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                String day = update.getCallbackQuery().getData();
                List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
                Lesson lesson = lessons.get(lessons.size() - 1);
                LessonSchedule lessonSchedule = new LessonSchedule();
                if (day.contains(WeekDay.MONDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.MONDAY.getDay());
                } else if (day.contains(WeekDay.TUESDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.TUESDAY.getDay());
                } else if (day.contains(WeekDay.WEDNESDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.WEDNESDAY.getDay());
                } else if (day.contains(WeekDay.THURSDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.THURSDAY.getDay());
                } else if (day.contains(WeekDay.FRIDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.FRIDAY.getDay());
                } else if (day.contains(WeekDay.SATURDAY.getDay())) {
                    lessonSchedule.setDay(WeekDay.SATURDAY.getDay());
                }
                lessonSchedule.setChildId(userId);
                List<LessonSchedule> listLessonSchedule = new ArrayList<>();
                listLessonSchedule.add(lessonSchedule);
                lesson.setLessonSchedules(listLessonSchedule);
                lessonScheduleService.create(lessonSchedule);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                InlineKeyboardMarkup inline = inlineAddExtraDay.getInline();
                String response = "Давай добавим время начала урока";
                return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, inline, response);
            } else if (child.getState().equals(State.ADD_EVENT_TIME.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                String time = update.getMessage().getText();
                List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
                Lesson lesson = lessons.get(lessons.size() - 1);
                int size = lesson.getLessonSchedules().size() - 1;
                LessonSchedule lessonSchedule = lesson.getLessonSchedules().get(size);
                lessonSchedule.setLessonStartTime(parseTime(time));
                List<LessonSchedule> lessonSchedules = new ArrayList<>();
                lessonSchedules.add(lessonSchedule);
                lesson.setLessonSchedules(lessonSchedules);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                InlineKeyboardMarkup inline = inlineAddEvenTime.getInline();
                String response = "Готово! Урок добавлен в твое расписание!";
                return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
            } else if (child.getState().contains(State.EDIT_SPECIFIC_EVENT_FIELD.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                String oldTitle = parseFieldTitle(child.getState());
                Lesson lesson = lessonService.findLessonByTitle(userId, oldTitle);
                String newTitle = update.getMessage().getText();
                lesson.setTitle(newTitle);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
                new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
                String response = "Название урока успешно сохранили! Пойду поем!";
                ReplyKeyboardMarkup menuButton = replyMainMenuButton.getMainMenuButton();
                return new MessageSenderImpl().sendMessageWithReply(userId, response, menuButton);
            } else if (child.getState().contains(State.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                String title = parseFieldTitle(child.getState());
                Lesson lesson = lessonService.findLessonByTitle(userId, title);
                String teacherName = update.getMessage().getText();
                lesson.setTeacher(teacherName);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                childService.create(child);
                String response = "Учителя изменили!";
                ReplyKeyboardMarkup menuButton = replyMainMenuButton.getMainMenuButton();
                String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
                new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
                return new MessageSenderImpl().sendMessageWithReply(userId, response, menuButton);
            } else if (child.getState().contains(State.EDIT_SPECIFIC_EVENT_START_TIME.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                String title = hasReply ? update.getMessage().getText() : parseEventTitle(
                        update.getCallbackQuery().getMessage().getText());
                LocalTime localTime = parseTimeWithTitle(update.getCallbackQuery().getData());
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                Lesson lesson = lessonService.findLessonByTitle(userId, title);
                List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
                LessonSchedule specificLessonSchedule = lessonSchedules.stream()
                        .filter(lessonSchedule -> lessonSchedule.getLessonStartTime().equals(localTime))
                        .findFirst()
                        .get();
                specificLessonSchedule.setLessonStartTime(LocalTime.MIN);
                lessonScheduleService.create(specificLessonSchedule);
                child.setIsSkip(false);
                child.setState(State.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState()
                        + LessonService.COLON_SEPARATOR + lesson.getTitle());
                childService.create(child);
                String response = "Можешь ввести новое время для урока <i>\"" + lesson.getTitle() + "\"</i>";
                return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
            } else if (child.getState().contains(State.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
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
                String response = "Время начала урока изменили!";
                ReplyKeyboardMarkup menuButton = replyMainMenuButton.getMainMenuButton();
                String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
                new MessageSenderImpl().sendMessageViaURL(userId, infoLesson);
                return new MessageSenderImpl().sendMessageWithReply(userId, response, menuButton);
            } else if (child.getState().contains(State.ADD_SPECIFIC_EVENT_START_TIME.getState())
                    && !generalCommands.contains(replyCommand)
                    && !child.getIsSkip()) {
                String title = parseTitleFromState(child.getState());
                LocalTime localTime = parseTime(update.getMessage().getText());
                Lesson lesson = lessonService.findLessonByTitle(userId, title);
                List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
                LessonSchedule lessonSchedule = new LessonSchedule();
                lessonSchedule.setLessonStartTime(localTime);
                lessonSchedule.setChildId(userId);
                lessonSchedules.add(lessonSchedule);
                lessonScheduleService.create(lessonSchedule);
                lessonService.create(lesson);
                child.setState(State.NO_STATE.getState());
                child.setIsSkip(false);
                childService.create(child);
                String response = "Новое время уже добавили для урока <i>\"" + lesson.getTitle() + "\"</i>,"
                        + " а напомни мне для какого дня недели это время?";
                InlineKeyboardMarkup inline = addSpecificEventStartTimeButton.getInline();
                return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
            }
            child.setIsSkip(true);
            childService.create(child);
            if (hasReply) {
                replyCommand = update.getMessage().getText();
                CommandHandler commandHandler = commandStrategy.get(replyCommand);
                return commandHandler.handle(update);
            } else if (hasCallback) {
                replyCommand = update.getCallbackQuery().getData();
                InlineHandler inlineHandler = inlineStrategy.get(replyCommand);
                return inlineHandler.handle(update);
            }
            return null;
        }
        return null;
    }


    private String parseEventTitle(String name) {
        return name.split(LessonService.QUOTE_SEPARATOR)[1];
    }

    private String parseTitleFromState(String name) {
        return name.split(LessonService.COLON_SEPARATOR)[1];
    }

    private String parseFieldTitle(String name) {
        return name.split(LessonService.COLON_SEPARATOR)[1];
    }

    private LocalTime parseTimeWithTitle(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
    }

    private LocalTime parseTime(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}

//            String[] split = replyCommand.split(SEPARATOR);
//            if (hashSet.contains(split[0])) {
//                Child child = childService.getById(Long.valueOf(split[2]));
//                if (!child.getState().equals(ChildService.NO_STATE)) {
//                    if (child.getState().equals(Command.EDIT_SCHEDULE_EVENT_FIELD.getName())) {
//                        lessonService.getById()
//                    }
//                }
//
//            }

