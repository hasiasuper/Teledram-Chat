package com.linskyi;

import com.linskyi.Action.ActionChat;
import com.linskyi.Action.ActionNull;
import com.linskyi.Action.ActionNumberRoom;
import com.linskyi.Action.ActionReg;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Map;

public class ChatBot extends TelegramLongPollingBot {

    public static Map<Long, User> listUsers = new HashMap<>();
    public static Map<Long, Long> listOnline = new HashMap<>();
    public static int userID = 1;
    public static int roomID = 1;
    public static Map<Integer, Room> listRooms = NewRoom.createGeneralRoom();


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi chatBot = new TelegramBotsApi();
        try {
            chatBot.registerBot(new ChatBot());
        } catch (TelegramApiRequestException e) {
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

            if (listUsers.get(m.getChatId()) == null)
                listUsers.put(m.getChatId(), new User(m.getChatId()));

            if (listUsers.get(m.getChatId()).getAction() == null)
                ActionNull.run(m);
            else if (listUsers.get(m.getChatId()).getAction().equals("reg"))
                ActionReg.run(m);
            else if (listUsers.get(m.getChatId()).getAction().equals("chat"))
                ActionChat.run(m);
            else
                ActionNumberRoom.run(m);

        }


    }

    @Override
    public String getBotUsername() {
        return ReadProperties.check()[0];
    }

    @Override
    public String getBotToken() {
        return ReadProperties.check()[1];
    }
}
