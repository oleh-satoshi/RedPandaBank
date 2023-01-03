package com.example.redpandabank.service;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UrlMessageSenderImpl {//implements MessageSender {
   // @Override
    public void sendToTelegram(Long chatId, String content) {
//        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
//
//        String apiToken = "5805955090:AAF2S1GZ5CUBsbZnoSFxyzjUrsHgyWZAXws";
//
//        String idChat = chatId.toString();
//
//        content = "<sup>superscript</sup>";
//
//        urlString = String.format(urlString, apiToken, idChat, content);
//
//        try {
//            URL url = new URL(urlString);
//            URLConnection conn = url.openConnection();
//            InputStream is = new BufferedInputStream(conn.getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException("Can't send message! ", e);
//        }
    }
}
