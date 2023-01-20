package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.ChildService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class StartCommandHandler implements CommandHandler<Update> {
    private final ReplyMainMenuButton replyMainMenuButton;
    private final ChildService childService;
    private boolean isInitialize;

    public StartCommandHandler(ReplyMainMenuButton replyMainMenuButton,
                               ChildService childService) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.childService = childService;
    }

    @Override
    public SendMessage handle(Update update) {
        String response;
        Long userId = update.getMessage().getChatId();
        isInitialize = childService.findById(userId);
        if (!isInitialize) {
            childService.createChild(userId);
            response = EmojiParser.parseToUnicode("\"Гаааррр!\" :grinning: "
                    + "Это значит \"Привет!\" на языке панд :stuck_out_tongue: \n\n"
                    + "В нашем :deciduous_tree: лесу меня знают все, такая уж у меня работа :card_index_dividers: \n\n"
                    + "В детстве мне всегда нравилось мирить моих сестричек и братьев друг с другом, "
                    + "а иногда и с родителями :sweat_smile: \n"
                    + "а сегодня это стало моей работой и я продолжаю помогать родителям и детям дружить. "
                    + "Я так люблю свою работу! :sparkling_heart: \n\n"
                    + "Ой..Что-то я отвлекся, в общем будем с тобой друзьями! :handshake:  \n\n"
                    + "Хоть у меня и пушистые :paw_prints: лапки мне все равно не сложно помогать "
                    + "друзьям выполнять их домашние дела, я буду напоминать тебе сделать что-то, "
                    + "а потом я буду смотреть как мой любимый друг справился с заданием! И да, иногда твои "
                    + "родители тоже будут проверять твою работу! \n\n"
                    + "А еще у меня будут хранится твои :moneybag: денежки! "
                    + "Я всегда готов передать их твоим родителям когда ты этого захочешь! "
                    + "Тебе нужно будет просто нажать кнопочку \"Обменять!\" "
                    + "Я уверен что нам будет весело! \n\n"
                    + "Ты всегда можешь мне написать и я тебе отвечу! "
                    + "Я придумал для тебя специальные кнопочки что бы тебе было удобнее писать мне! "
                    + "Я приготовил себе сочнейший кусок бамбука и собираюсь садиться :fork_knife_plate: кушать, "
                    + "а ты пока что осмотрись тут и помни что я всегда откликнусь когда ты меня позовешь! "
                    ) ;
            SendMessage sendMessage =  SendMessage.builder()
                    .text(response)
                    .chatId(userId)
                    .replyMarkup(replyMainMenuButton.getMainMenuButton())
                    .build();
            return sendMessage;
        } else {
            response = EmojiParser.parseToUnicode("Эй! Мы же с тобой познакомились уже! Ты что забыл? :sweat_smile:");
            return SendMessage.builder()
                    .text(response)
                    .parseMode("HTML")
                    .chatId(userId)
                    .build();
        }
    }

}
