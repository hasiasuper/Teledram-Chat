package com.linskyi.action;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ListIterator;

import static com.linskyi.CreateChatBotRun.bot;

public class ActionNumberRoom {

    public static void run(Message m, DBObject user) {
        boolean remove = false;
        int removeID = 0;

        DBCursor cur = bot.tableRooms.find();
        DBObject room;
        while (cur.hasNext()) {
            room = cur.next();
            if (user.get("action").equals(room.get("_id"))) {
                if (m.getText().equals("/exit")) {
                    DBObject doc = bot.tableRooms.findOne(new BasicDBObject("_id", user.get("action")));
                    ListIterator<Object> trustedList = ((BasicDBList) doc.get("online")).listIterator();
                    while (trustedList.hasNext()) {
                        Object nextItem = trustedList.next();
                        if (nextItem.equals(m.getChatId())) {
                            bot.sendMsg((Long) user.get("_id"), "Вы вышли, заходите к нам ещё!");
                        } else
                            bot.sendMsg((Long) nextItem, "Нас покидает " + user.get("nickname") + "[" + user.get("id") + "], помашем ему(ей) ручкой!");
                    }
                    remove = true;
                    removeID = (Integer) room.get("_id");
                    break;
                } else {
                    DBObject doc = bot.tableRooms.findOne(new BasicDBObject("_id", user.get("action")));
                    ListIterator<Object> trustedList = ((BasicDBList) doc.get("online")).listIterator();
                    while (trustedList.hasNext()) {
                        Object nextItem = trustedList.next();
                        if (!nextItem.equals(m.getChatId()))
                            bot.sendMsg((Long) nextItem, user.get("nickname") + "[" + user.get("id") + "]: " + m.getText());
                        break;
                    }
                }
            }
        }

        if (remove) {
            BasicDBObject newAction = new BasicDBObject();
            newAction.append("$set", new BasicDBObject().append("action", null));
            BasicDBObject searchQuery2 = new BasicDBObject().append("_id", m.getChatId());
            bot.tableUsers.update(searchQuery2, newAction);

            bot.tableOnline.remove(bot.tableOnline.findOne(m.getChatId()));

            BasicDBObject removeDocument = new BasicDBObject();
            removeDocument.append("$pull", new BasicDBObject().append("online", m.getChatId()));
            BasicDBObject searchQuery = new BasicDBObject().append("_id", removeID);
            bot.tableRooms.update(searchQuery, removeDocument);
        }
    }
}
