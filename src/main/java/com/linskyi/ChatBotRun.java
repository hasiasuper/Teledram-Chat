package com.linskyi;

import com.linskyi.action.*;
import com.linskyi.objects.*;
import com.mongodb.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.net.UnknownHostException;

import static com.linskyi.CreateChatBotRun.bot;


public class ChatBotRun extends TelegramLongPollingBot {
    public static BotProperties botProperties = PropertyReader.read();
    public static MongoClient mongoClient;

    static {
        try {
            mongoClient = new MongoClient(botProperties.getBotHost(), botProperties.getBotPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //todo: вилучити в окремий класс роботу з БД
    public DB db = mongoClient.getDB(botProperties.getBotDBName());
    public DBCollection tableUsers = db.getCollection("users");
    public DBCollection tableOnline = db.getCollection("online");
    public DBCollection tableRooms = db.getCollection("rooms");

    public static void main(String[] args) {
        ApiContextInitializer.init();
        NewRoom.createGeneralRoom();
        TelegramBotsApi chatBot = new TelegramBotsApi();
        try {
            chatBot.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo: вилучити в окремий класс роботу з апі телегама
    public void sendMsg(Long chatID, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID.toString());
        sendMessage.setText(text);
        try {
            //todo: найти спосіб не використовувати дерпрекейтед метод
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message m = update.getMessage();

        if (m.getText() == null) {
            sendMsg(m.getChatId(), "Чат распознает только текстовые сообщения и смайлики!");
        } else {

            DBObject query = new BasicDBObject();
            query.put("_id", m.getChatId());
            DBObject user = tableUsers.findOne(query);

            if (user == null) {
                BasicDBObject document = new BasicDBObject();
                document.put("_id", m.getChatId());
                tableUsers.insert(document);
                user = tableUsers.findOne(query);
            }

            if (user.get("action") == null)
                ActionNull.run(m, user);
            else if (user.get("action").equals("reg"))
                ActionReg.run(m);
            else if (user.get("action").equals("chat"))
                ActionChat.run(m, user);
            else
                ActionNumberRoom.run(m, user);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getBotToken();
    }
}
