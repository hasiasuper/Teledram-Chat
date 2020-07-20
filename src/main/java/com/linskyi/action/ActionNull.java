package com.linskyi.action;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.telegram.telegrambots.api.objects.Message;

import static com.linskyi.CreateChatBotRun.bot;

public class ActionNull {

    public static void run(Message m, DBObject user) {
        if (!("/chat".equals(m.getText()) || "/reg".equals(m.getText()) || "/exit".equals(m.getText())))
            bot.sendMsg(m.getChatId(), "Вас приветствует тестовый чат. \nКоманды: \n/chat - вход в чат \n/reg - регистрация в чате \n/exit - выход из чата");

        if ("/chat".equals(m.getText())) {
            if (user.get("nickname") == null)
                bot.sendMsg(m.getChatId(), "Вы не зарегистрированы, пожалуйста, зарегистрируйтесь - /reg");
            else {
                BasicDBObject newAction = new BasicDBObject();
                newAction.append("$set", new BasicDBObject().append("action", "chat"));
                BasicDBObject searchQuery = new BasicDBObject().append("_id", m.getChatId());
                bot.tableUsers.update(searchQuery, newAction);

                BasicDBObject document = new BasicDBObject();
                document.put("_id", m.getChatId());
                bot.tableOnline.insert(document);

                String temp = "Вы в чате! \nВыберите номер комнаты: \n";
                DBObject temp2;

                DBCursor cur = bot.tableRooms.find();
                while (cur.hasNext()) {
                    temp2 = cur.next();
                    temp = temp.concat("[" + temp2.get("_id") + "] " + temp2.get("title") + "\n");
                }
                bot.sendMsg(m.getChatId(), temp);
            }
        } else if ("/reg".equals(m.getText())) {
            if (user.get("nickname") == null) {
                BasicDBObject newAction = new BasicDBObject();
                newAction.append("$set", new BasicDBObject().append("action", "reg"));
                BasicDBObject searchQuery = new BasicDBObject().append("_id", m.getChatId());
                bot.tableUsers.update(searchQuery, newAction);
                bot.sendMsg(m.getChatId(), "Введите желаемый никнейм!");
            } else
                bot.sendMsg(m.getChatId(), "Вы уже зарегистрированы!");
        } else if ("/exit".equals(m.getText())) {
            bot.sendMsg(m.getChatId(), "Вы же не в чате!");
        }
    }
}
