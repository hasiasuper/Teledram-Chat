package com.linskyi.action;

import com.linskyi.ChatBotRun;
import com.linskyi.objects.Room;
import org.telegram.telegrambots.api.objects.Message;

import static com.linskyi.ChatBotRun.listOnline;
import static com.linskyi.ChatBotRun.listRooms;
import static com.linskyi.ChatBotRun.listUsers;

import java.util.Map;

public class ActionNull {

    private static ChatBotRun bot = new ChatBotRun();

    public static void run(Message m) {
        if (!("/chat".equals(m.getText()) || "/reg".equals(m.getText()) || "/exit".equals(m.getText())))
            bot.sendMsg(m.getChatId(), "Вас приветствует тестовый чат. \nКоманды: \n/chat - вход в чат \n/reg - регистрация в чате \n/exit - выход из чата");

        if ("/chat".equals(m.getText())) {
            if (listUsers.get(m.getChatId()).getNickname() == null)
                bot.sendMsg(m.getChatId(), "Вы не зарегистрированы, пожалуйста, зарегистрируйтесь - /reg");
            else {
                listUsers.get(m.getChatId()).setAction("chat");
                listOnline.put(m.getChatId(), m.getChatId());
                String temp = "Вы в чате! \nВыберите номер комнаты: \n";

                for (Map.Entry<Integer, Room> room : listRooms.entrySet()) {
                    temp = temp.concat("[/" + room.getKey() + "] " + room.getValue().getTitleRoom() + "\n");
                }
                bot.sendMsg(m.getChatId(), temp);
            }
        } else if ("/reg".equals(m.getText())) {
            if (listUsers.get(m.getChatId()).getNickname() == null) {
                listUsers.get(m.getChatId()).setAction("reg");
                bot.sendMsg(m.getChatId(), "Введите желаемый никнейм!");
            } else
                bot.sendMsg(m.getChatId(), "Вы уже зарегистрированы!");
        } else if ("/exit".equals(m.getText())) {
            bot.sendMsg(m.getChatId(), "Вы же не в чате!");
        }
    }
}
