package com.linskyi;

import com.linskyi.objects.BotProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.linskyi.ChatBotRun.botProperties;

public class PropertyReader {
    public static BotProperties read() {
        Properties property = new Properties();
        try {
            FileInputStream application = new FileInputStream("src/main/resources/application.properties");
            property.load(application);
            botProperties = new BotProperties(property.getProperty("telegram.bot.name"),
                    property.getProperty("telegram.bot.token"),
                    property.getProperty("telegram.bot.host"),
                    Integer.valueOf(property.getProperty("telegram.bot.port")),
                    property.getProperty("telegram.bot.dbname"));
            application.close();
        } catch (IOException e) {
            System.err.println("Ошибка связанная с файлом application.properties");
        }
        return botProperties;
    }
}