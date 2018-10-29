package com.linskyi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

    public static String[] check() {

        Properties property = new Properties();
        String[] bot = new String[2];

        try {
            FileInputStream application = new FileInputStream("src/main/resources/application.properties");
            property.load(application);
            bot[0] = property.getProperty("telegram.bot.name");
            bot[1] = property.getProperty("telegram.bot.token");
            application.close();
        } catch (IOException e) {
            System.err.println("Ошибка связанная с файлом application.properties");
        }
        return bot;
    }
}