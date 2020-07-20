package com.linskyi;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import static com.linskyi.CreateChatBotRun.bot;

public class NewUser {

    public static String reg(String nickname, Long id) {

        DBObject query = new BasicDBObject("nickname", new BasicDBObject("$exists", true));
        int userID = bot.tableUsers.find(query).count() + 1;

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("nickname", nickname);
        newDocument.put("id", userID);
        BasicDBObject searchQuery = new BasicDBObject().append("_id", id);
        bot.tableUsers.update(searchQuery, newDocument);

        return "Поздравляем, " + nickname + ", вы успешно зарегистрированы! Ваш id в чате [" + userID + "]. Теперь вы можете войти в чат - /chat";
    }
}
