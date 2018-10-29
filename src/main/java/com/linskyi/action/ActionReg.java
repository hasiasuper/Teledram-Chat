package com.linskyi.action;

import com.linskyi.ChatBotRun;
import com.linskyi.NewUser;
import org.telegram.telegrambots.api.objects.Message;

import static com.linskyi.ChatBotRun.*;

public class ActionReg {
    private static ChatBotRun bot = new ChatBotRun();

    public static void run(Message m) {
        bot.sendMsg(m.getChatId(), NewUser.reg(m.getText(), m.getChatId(), userID, null));
        userID++;

        //???????? анкета

    }
}
