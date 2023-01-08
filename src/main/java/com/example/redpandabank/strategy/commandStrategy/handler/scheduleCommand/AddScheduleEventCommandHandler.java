package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.schedule.InlineAddEventByWeekday;
import com.example.redpandabank.keyboard.main.BackToMainMenuButton;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.time.LocalTime;
import java.util.List;

@Component
public class AddScheduleEventCommandHandler implements CommandHandler<Update> {
    private final LessonService lessonService;
    private final BackToMainMenuButton backToMainMenuButton;
    private final MessageSender messageSender;
    private final InlineAddEventByWeekday inlineAddEventByWeekday;
    private final LessonScheduleService lessonScheduleService;


    public AddScheduleEventCommandHandler(LessonService lessonService,
                                          BackToMainMenuButton backToMainMenuButton,
                                          MessageSender messageSender,
                                          InlineAddEventByWeekday inlineAddEventByWeekday,
                                          LessonScheduleService lessonScheduleService) {
        this.lessonService = lessonService;
        this.backToMainMenuButton = backToMainMenuButton;
        this.messageSender = messageSender;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
        this.lessonScheduleService = lessonScheduleService;
    }

    @Override
    public SendMessage handle(Update update) {
        String title;
        String teacher;
        LocalTime startTime;
        Integer duration;
        String response;
        Long userId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        if (text.contains(Command.ADD_SCHEDULE_EVENT.getName())) {
            response = "Давай назовем новый урок, как ты говоришь он у тебя записан в дневнике? "
                    + "Я сохраню это название в базу данных и буду тебе его показывать каждый раз "
                    + "когда ты захочешь посмотреть расписание! \n"
                    + "Я тебе прислал название команды выше этого сообщения, можешь просто скопировать и отправить "
                    + "эту команду написав рядом название урока"
                    + " , например: \n\n <i>/saveName Maтематика</i> \n\n или вот еще: \n <i>/saveName Лесоведенье</i>";
           // messageSender.sendToTelegram(userId, Command.SAVE_EVENT_NAME.getName());
            return SendMessage.builder()
                    .chatId(userId)
                    .parseMode("HTML")
                    .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                    .text(response)
                    .build();
            }
        if (text.contains(Command.SAVE_EVENT_NAME.getName())) {
            if (text.trim().equals(Command.SAVE_EVENT_NAME.getName())) {
                response = "Хорошо, команду ты отправил, пол дела сделано, "
                        + "сейчас давай добавим к ней название урока и должно получится так:   "
                        + "/saveName Бамбукоматика";
               // messageSender.sendToTelegram(userId, response);
            } else {
                title = text.substring(Command.SAVE_EVENT_NAME.getName().length()).trim();

                if (lessonService.findAllByTitle(title, userId)) {
                    Lesson lesson = new Lesson();
                    lesson.setChildId(userId);
                    lessonService.create(lesson);
                    List<Lesson> lessons = lessonService.findAllByChildId(userId);
                    Lesson  lesson1 = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                    lesson1.setTitle(title);
                    lessonService.create(lesson1);
                } else {
                    response = "Такой урок уже сохраняли!";
                    return new SendMessage().builder()
                            .text(response)
                            .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                            .parseMode("HTML")
                            .chatId(userId)
                            .build();
                }
                response = "А как зовут твоего учителя?";
                //messageSender.sendToTelegram(userId, Command.SAVE_EVENT_TEACHER_NAME.getName());
                return new SendMessage().builder()
                        .text(response)
                        .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            }
        } else if (text.contains(Command.SAVE_EVENT_TEACHER_NAME.getName())) {
            if (text.trim().equals(Command.SAVE_EVENT_TEACHER_NAME.getName())) {
                //TODO сделай отправку через MessageSender
                response = "Давай напишем имя учителя!";
                //dfsmessageSender.sendToTelegram(userId, response);
            } else {
                teacher = text.substring(Command.SAVE_EVENT_TEACHER_NAME.getName().length()).trim();
                List<Lesson> lessons = lessonService.findAllByChildId(userId);
                Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                lesson.setTeacher(teacher);
                lesson.setLessonId(lesson.getLessonId());
                lessonService.create(lesson);
                response = "А мне уже успели рассказать что ты трудолюбец, а тут я уже и сам вижу!\n"
                        + "Описание сохранили! Теперь мне нужно знать сколько длится урок, должно быть так: \n" +
                        Command.SAVE_EVENT_DURATION.getName()  + " 45";
                return SendMessage.builder()
                        .text(response)
                        .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            }
        } else if (text.contains(Command.SAVE_EVENT_DURATION.getName())) {
            if (text.trim().equals(Command.SAVE_EVENT_DURATION.getName())) {
                response = "Обязательно добавь длительность, это нужно что бы я знал когда присылать тебе напоминания!";
                return SendMessage.builder()
                        .text(response)
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            } else {
                duration = Integer.valueOf(text.substring(13).trim());
                List<Lesson> lessons = lessonService.findAllByChildId(userId);
                Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                lesson.setDuration(duration);
                //чекни айди тут
                lesson.setLessonId(lesson.getLessonId());
                lessonService.create(lesson);
                response = "Длительность урока сохранили! Теперь давай передадим время начала урока, должно быть так:\n" +
                        Command.SAVE_EVENT_SCHEDULE.getName() + " 10:45";
                return SendMessage.builder()
                        .text(response)
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            }
        } else if (text.contains(Command.SAVE_EVENT_SCHEDULE.getName())) {
            if (text.trim().equals(Command.SAVE_EVENT_SCHEDULE.getName())) {
                response = "Обязательно напиши время начала урока," +
                        " это нужно что бы я знал когда присылать тебе напоминания!";
                return SendMessage.builder()
                        .text(response)
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            } else {
                startTime = parseTime(text);
                LessonSchedule lessonSchedule = new LessonSchedule();
                lessonSchedule.setChildId(userId);
                lessonSchedule.setLessonStartTime(startTime);
                List<Lesson> lessons = lessonService.findAllByChildId(userId);
                Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                lesson.getLessonSchedules().add(lessonSchedule);
                lessonScheduleService.create(lessonSchedule);
                lessonService.create(lesson);

                //чекни айди тут
                response = "Время начала урока сохранили! Теперь выбери в какой день будет этот урок";
                return new SendMessage().builder()
                        .chatId(userId)
                        .text(response)
                        .replyMarkup(inlineAddEventByWeekday.getInline())
                        .parseMode("HTML")
                        .build();
                }

        }
        return null;
    }

    private LocalTime parseTime(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.substring(13).trim().split(":");
        return LocalTime.of(Integer.valueOf(response[0]), Integer.valueOf(response[1]));
    }
}
