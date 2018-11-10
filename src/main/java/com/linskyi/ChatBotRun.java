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

public class ChatBotRun extends TelegramLongPollingBot {
    public static BotProperties botProperties = PropertyReader.read();
    public static MongoClient mongoClient;
    public DB db = mongoClient.getDB(botProperties.getBotDBName());
    public DBCollection tableUsers = db.getCollection("users");
    public DBCollection tableOnline = db.getCollection("online");
    public DBCollection tableRooms = db.getCollection("rooms");

    {
        try {
            mongoClient = new MongoClient(botProperties.getBotHost(), botProperties.getBotPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        NewRoom.createGeneralRoom();
        TelegramBotsApi chatBot = new TelegramBotsApi();
        try {
            chatBot.registerBot(new ChatBotRun());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Long chatID, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID.toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message m = update.getMessage();

        if (m.getText() == null) {
            sendMsg(m.getChatId(), "В данный момент чат распознает только текстовые сообщения!");
        } else {

            if (tableUsers.find().count() == 0) {
                BasicDBObject document = new BasicDBObject();
                document.put("_id", m.getChatId());
                tableUsers.insert(document);
            }

            DBObject query2 = new BasicDBObject();
            query2.put("_id", m.getChatId());
            DBObject user = tableUsers.findOne(query2);

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
