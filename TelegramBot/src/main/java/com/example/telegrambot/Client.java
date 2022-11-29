package com.example.telegrambot;

import it.grabz.grabzit.GrabzItClient;
import it.grabz.grabzit.parameters.AnimationOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

//?url=https://www.youtube.com/watch?v=epepDBIAJD8&ab&timeStart=00:01:02&timeEnd=00:01:06
@RestController
public class Client {
    private static final Map<String, String> getenv = System.getenv();
    @GetMapping(value = "/video",
            produces = MediaType.IMAGE_GIF_VALUE
    )
    public static byte[] videoFun(@RequestParam String url, @RequestParam String timeStart, @RequestParam String timeEnd) throws Exception {
        GrabzItClient grabzIt = new GrabzItClient(getenv.get("KEY"), getenv.get("SECRET"));
        AnimationOptions options = new AnimationOptions();
        int start = timeToSeconds(timeStart);
        int end = timeToSeconds(timeEnd);
        int dur = end - start;
        options.setFramesPerSecond(10);
        options.setDuration(dur);
        options.setStart(start);
        options.setRepeat(1);
        options.setHeight(300);
        options.setWidth(400);
        grabzIt.URLToAnimation(url, options);
        byte[] result = null;
        for (int i = 0; i < 3; i++) {
            try {
                if (result == null) {
                    result = grabzIt.SaveTo().getBytes();
                }
                break;
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static int timeToSeconds(String time) {
        int result;
        int[] output = {0, 0, 0};
        String[] parts = time.split(":");
        for (int x = 0; x < parts.length; x++) {
            if (x >= output.length)
                break;
            output[x] = Integer.parseInt(parts[x]);
        }
        result = output[0] * 3600 + output[1] * 60 + output[2];
        return result;
    }
    @GetMapping(value = "/help")
    public static String helpFun() {
        return "/video - делает из видео gif-ки. Для этого надо вставить ссылку,"+
                "желаемое время начала и конца воспроизведения в формате 00:00:00. Видео не дольше 5 минут" +
                "/монетка - помогает приянть жизненноважные решения"+
                "/help - справочная";
    }
}