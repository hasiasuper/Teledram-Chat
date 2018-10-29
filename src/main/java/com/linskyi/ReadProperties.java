package com.linskyi;

import com.linskyi.objects.MyProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.linskyi.ChatBotRun.myProperties;

public class ReadProperties {

    public static MyProperties read() {
        Properties property = new Properties();
        try {
            FileInputStream application = new FileInputStream("src/main/resources/application.properties");
            property.load(application);
            myProperties = new MyProperties(property.getProperty("telegram.bot.name"), property.getProperty("telegram.bot.token"));
            application.close();
        } catch (IOException e) {
            System.err.println("Ошибка связанная с файлом application.properties");
        }
        return myProperties;
    }
}