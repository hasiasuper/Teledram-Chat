package com.linskyi.action;

import com.linskyi.ChatBotRun;
import com.linskyi.objects.Room;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Map;

import static com.linskyi.ChatBotRun.listOnline;
import static com.linskyi.ChatBotRun.listRooms;
import static com.linskyi.ChatBotRun.listUsers;

public class ActionNumberRoom {
    private static ChatBotRun bot = new ChatBotRun();

    public static void run(Message m) {
        boolean remove = false;
        int temp = 0;

        for (Map.Entry<Integer, Room> room : listRooms.entrySet())
            try {
                if (listUsers.get(m.getChatId()).getAction().equals(room.getKey().toString())) {
                    Map<Long, Long> userRoom = room.getValue().getListOnline();

                    if (m.getText().equals("/exit")) {
                        for (Map.Entry<Long, Long> user : userRoom.entrySet()) {
                            if (user.getValue().equals(m.getChatId())) {
                                bot.sendMsg(user.getValue(), "Вы вышли, заходите к нам ещё!");
                            } else
                                bot.sendMsg(user.getValue(), "Нас покидает " + listUsers.get(m.getChatId()).getNickname() + "[" + listUsers.get(m.getChatId()).getChatID() + "], помашем ему(ей) ручкой!");
                        }
                        remove = true;
                        temp = room.getKey();
                        break;
                    } else {
                        for (Map.Entry<Long, Long> user : userRoom.entrySet())
                            if (!user.getKey().equals(m.getChatId()))
                                bot.sendMsg(user.getValue(), listUsers.get(m.getChatId()).getNickname() + "[" + listUsers.get(m.getChatId()).getChatID() + "]: " + m.getText());
                        break;
                    }
                }
            } catch (NullPointerException ignored) {
            }

        if (remove) {
            listUsers.get(m.getChatId()).setAction(null);
            listOnline.remove(m.getChatId());
            listRooms.get(temp).getListOnline().remove(m.getChatId());
        }
    }
}
