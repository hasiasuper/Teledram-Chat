package com.linskyi.Action;

import com.linskyi.ChatBot;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Map;

import static com.linskyi.ChatBot.listOnline;
import static com.linskyi.ChatBot.listRooms;
import static com.linskyi.ChatBot.listUsers;

public class ActionChat {
    private static ChatBot bot = new ChatBot();

    public static void run(Message m) {
        if (m.getText().equals("/exit")) {
            bot.sendMsg(listUsers.get(m.getChatId()).getId(), "Вы вышли, заходите к нам ещё!");
            listUsers.get(m.getChatId()).setAction(null);
            listOnline.remove(m.getChatId());
        } else {
            int temp = 0;
            try {
                Integer temp2 = Integer.valueOf(m.getText());

                if (listRooms.get(temp2) != null) {

                    Map<Long, Long> userRoom = listRooms.get(temp2).getListOnline();

                    for (Map.Entry<Long, Long> user : userRoom.entrySet())
                        bot.sendMsg(user.getValue(), "К нам приходит " + listUsers.get(m.getChatId()).getNickname() + "[" + listUsers.get(m.getChatId()).getChatID() + "], приветствуем его(её)!");

                    listRooms.get(temp2).getListOnline().put(m.getChatId(), m.getChatId());
                    listUsers.get(m.getChatId()).setAction(m.getText());
                    bot.sendMsg(m.getChatId(), "Вы в комнате: " + listRooms.get(temp2).getTitleRoom() + "[" + listRooms.get(temp2).getRoomID() + "] \n" + listRooms.get(temp2).getDescription());
                    temp = 1;
                }

                if (temp != 1)
                    bot.sendMsg(m.getChatId(), "Комнаты под таким номером не существует! Укажите существующую комнату!");

            } catch (NumberFormatException e) {
                bot.sendMsg(m.getChatId(), "Укажите номер комнаты цифрой!");
            }

        }
    }
}
