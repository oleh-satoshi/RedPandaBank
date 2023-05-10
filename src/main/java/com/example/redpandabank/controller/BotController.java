package com.example.redpandabank.controller;

import com.example.redpandabank.model.Translate;
import com.example.redpandabank.service.TelegramBot;
import com.example.redpandabank.service.TranslateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final TelegramBot telegramBot;
    private final TranslateService translateService;

    @PostMapping
    public BotApiMethod onUpdateReceivedParent(@RequestBody Update update) {
        return telegramBot. onWebhookUpdateReceived(update);
    }

    @GetMapping("/healthcheck")
    public List<Translate> healthcheck() {
        return translateService.findAll();
    }
}
