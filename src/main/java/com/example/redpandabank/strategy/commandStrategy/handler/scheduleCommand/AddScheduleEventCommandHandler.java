package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.buttons.schedule.InlineAddEventByWeekday;
import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
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


    public AddScheduleEventCommandHandler(LessonService lessonService,
                                          BackToMainMenuButton backToMainMenuButton,
                                          MessageSender messageSender,
                                          InlineAddEventByWeekday inlineAddEventByWeekday) {
        this.lessonService = lessonService;
        this.backToMainMenuButton = backToMainMenuButton;
        this.messageSender = messageSender;
        this.inlineAddEventByWeekday = inlineAddEventByWeekday;
    }

    @Override
    public SendMessage handle(Update update) {
        String title;
        String description;
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
            messageSender.sendToTelegram(userId, Command.SAVE_EVENT_NAME.getName());
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
                messageSender.sendToTelegram(userId, response);
            } else {
                title = text.substring(9).trim();
                Lesson lesson = new Lesson();
                lesson.setChildId(userId);
                lesson.setTitle(title);
                lessonService.create(lesson);
                response = "Давай пушистый пять! Мы сохранили название урока и можем идти дальше!\n\n"
                        + "Помню в школе иногда добавляли новые уроки и по названию я не всегда мог понять "
                        + "что именно мы тут учим, тогда я просто записывал в блокноте что мы делали на этом уроке "
                        + ", например: \n\n"
                        + "<i>\"Мы смотрели на бамбук под микроскопом и я еще не совсем понял что это за урок\"</i>\n\n"
                        + "<i>\"А тут мы читали рассказ \"Два совенка и ручеек\" скорее всего это лесная литература\"\n\n</i>"
                        + "Я также прислал тебе команду выше скопируй ее и допиши к ней описание урока, "
                        + "это конечно если ты хочешь, должно получиться так: \n\n"
                        + "<i>/saveDesc урок ХРВЛ - расшифровывается как Химические Реакции В Лесу - "
                        + "нам рассказывали почему осенние листики на полу вредные </i>\n\n"
                        + "правда полезно же!?";
                messageSender.sendToTelegram(userId, Command.SAVE_EVENT_DESCRIPTION.getName());
                return new SendMessage().builder()
                        .text(response)
                        .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                        .parseMode("HTML")
                        .chatId(userId)
                        .build();
            }
        } else if (text.contains(Command.SAVE_EVENT_DESCRIPTION.getName())) {
            if (text.trim().equals(Command.SAVE_EVENT_DESCRIPTION.getName())) {
                //TODO сделай отправку через MessageSender
                response = "Ага, все таки пустое описание мне отправил, ну тогда держи анекдот: "
                        + "Идёт слон в штанах. Навстречу муравей: - Слон, сними штаны. - Зачем? - "
                        + "Ну, сними, сними. Слон снял штаны. Муравей долго ползал по ним и говорит: "
                        + "- Надевай, слон, это не мои, мои с кармашками были.";
                messageSender.sendToTelegram(userId, response);
            } else {
                description = text.substring(9).trim();
                List<Lesson> lessons = lessonService.findAllByChildId(userId);
                Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                lesson.setDescription(description);
                lesson.setId(lesson.getId());
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
                lesson.setId(lesson.getId());
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
                List<Lesson> lessons = lessonService.findAllByChildId(userId);
                Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
                lesson.setLessonsStartTime(startTime);
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
        String[] response = text.substring(13).trim().split(":");
        return LocalTime.of(Integer.valueOf(response[0]), Integer.valueOf(response[1]));
    }
}
