package com.linskyi;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class NewRoom {
    private static ChatBotRun bot = new ChatBotRun();

    public static void createGeneralRoom() {
        DBObject query = new BasicDBObject("_id", 1);
        DBCursor check = bot.tableRooms.find(query);
        if (check.size() == 0) {
            BasicDBObject document1 = new BasicDBObject();
            document1.put("_id", 1);
            document1.put("title", "Гостинная");
            document1.put("description", "Приятного общения!");
            bot.tableRooms.insert(document1);

            BasicDBObject document2 = new BasicDBObject();
            document2.put("_id", 2);
            document2.put("title", "Кухня");
            document2.put("description", "Приятного общения!");
            bot.tableRooms.insert(document2);

            BasicDBObject document3 = new BasicDBObject();
            document3.put("_id", 3);
            document3.put("title", "Спальня");
            document3.put("description", "Приятного общения!");
            bot.tableRooms.insert(document3);
        }
    }

    //метод создания новых комнат

}
