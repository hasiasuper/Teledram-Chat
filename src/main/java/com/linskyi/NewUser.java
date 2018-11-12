package com.linskyi;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class NewUser {
    //todo обэкт чат бота маэ бути один на всю апплікуху
    private static ChatBotRun bot = new ChatBotRun();

    public static String reg(String nickname, Long id) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("nickname", nickname);
        BasicDBObject searchQuery = new BasicDBObject().append("_id", id);
        bot.tableUsers.update(searchQuery, newDocument);

        DBObject query = new BasicDBObject("nickname", new BasicDBObject("$exists", 1));
        int userID = bot.tableRooms.find(query).count() + 1;

        BasicDBObject newDocument2 = new BasicDBObject();
        newDocument2.append("$set", new BasicDBObject().append("id", userID));
        BasicDBObject searchQuery2 = new BasicDBObject().append("_id", id);
        bot.tableUsers.update(searchQuery2, newDocument2);

        return "Поздравляем, " + nickname + ", вы успешно зарегистрированы! Ваш id в чате [" + userID + "]. Теперь вы можете войти в чат - /chat";
    }

}
