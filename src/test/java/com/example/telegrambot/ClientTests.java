package com.example.telegrambot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class ClientTests {
    @Test
    void helpTest() {
        String expected = "/video - делает из видео gif-ки. Для этого надо вставить ссылку,\n" +
                "желаемое время начала и конца воспроизведения в формате 00:00:00. Видео не дольше 5 минут\n" +
                "/coin - помогает приянть жизненноважные решения\n" +
                "/motivation - поднимает боевой дух \n" +
                "/help - справочная";
        String result = Client.helpFun();
        Assertions.assertEquals(result, expected);
    }
    @Test
    void coinTest() {
        String true1 = "Yes";
        String true2 = "No";
        for (int i = 0; i < 4; i++) {
            String result = Client.coinFun();
            if (result.equals(true1)) {
                Assertions.assertEquals(result, true1);
            } else {
                Assertions.assertEquals(result, true2);
            }
        }
    }
}
