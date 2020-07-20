package com.linskyi.action;

import com.mongodb.*;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ListIterator;

import static com.linskyi.CreateChatBotRun.bot;

public class ActionChat {

    public static void run(Message m, DBObject user) {
        if (m.getText().equals("/exit")) {
            bot.sendMsg((Long) user.get("_id"), "Вы вышли, заходите к нам ещё!");

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("action", null));
            BasicDBObject searchQuery = new BasicDBObject().append("_id", m.getChatId());
            bot.tableUsers.update(searchQuery, newDocument);

            bot.tableOnline.remove(bot.tableOnline.findOne(m.getChatId()));
        } else {
            try {
                Integer roomID = Integer.valueOf(m.getText());
                DBObject query = new BasicDBObject("_id", roomID);
                DBCursor result = bot.tableRooms.find(query);
                DBObject numberRoom = null;
                while (result.hasNext()) {
                    numberRoom = result.next();
                }

                if (result.size() == 0) {
                    bot.sendMsg(m.getChatId(), "Комнаты под таким номером не существует! Укажите существующую комнату!");
                } else {
                    ListIterator<Object> onlineList;
                    try {
                        DBObject doc = bot.tableRooms.findOne(new BasicDBObject("_id", roomID));
                        onlineList = ((BasicDBList) doc.get("online")).listIterator();
                        while (onlineList.hasNext()) {
                            Object nextItem = onlineList.next();
                            bot.sendMsg((Long) nextItem, "К нам приходит " + user.get("nickname") + "[" + user.get("id") + "], приветствуем его(её)!");
                        }
                    } catch (NullPointerException e) {
                    }
                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.append("$push", new BasicDBObject().append("online", m.getChatId()));
                    BasicDBObject searchQuery = new BasicDBObject().append("_id", roomID);
                    bot.tableRooms.update(searchQuery, newDocument);

                    BasicDBObject newAction = new BasicDBObject();
                    newAction.append("$set", new BasicDBObject().append("action", roomID));
                    BasicDBObject searchQuery2 = new BasicDBObject().append("_id", m.getChatId());
                    bot.tableUsers.update(searchQuery2, newAction);

                    bot.sendMsg(m.getChatId(), "Вы в комнате: " + numberRoom.get("title") + "[" + numberRoom.get("_id") + "] \n" + numberRoom.get("description"));
                }
            } catch (NumberFormatException e) {
                bot.sendMsg(m.getChatId(), "Укажите номер комнаты цифрой!");
            }
        }
    }
}
