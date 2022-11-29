package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Service
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String BOT_NAME;
    @Value("${bot.token}")
    private String BOT_TOKEN;

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    @EventListener(SendMessage.class)
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String messageText = message.getText();
        String[] text = messageText.split(" ");

        if (update.hasMessage()) {
            try {
                if (message.hasText()) {
                    switch (text[0]) {
                        case "/start":
                            try {
                                execute(getStartMessage(chatId.toString()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        case "/video":
                            try {
                                execute(getVideoMessage(chatId.toString(), text[1], text[2], text[3]));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } else {
                    throw new IllegalArgumentException("Я понимаю только текст");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }




    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Привет, меня зовут Yor. Чем могу помочь?");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendAnimation getVideoMessage(String chatId, String url, String start, String end) throws Exception {
        byte[] result = Client.videoFun(url, start, end);
        InputStream is = new ByteArrayInputStream(result);
        InputFile f = new InputFile();
        f.setMedia(is, System.currentTimeMillis() + ".gif");
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setChatId(chatId);
        sendAnimation.setAnimation(f);
        return sendAnimation;
    }
}
