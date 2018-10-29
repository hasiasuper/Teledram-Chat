package com.linskyi.Action;

import com.linskyi.ChatBot;
import com.linskyi.NewUser;
import org.telegram.telegrambots.api.objects.Message;

import static com.linskyi.ChatBot.*;

public class ActionReg {
    private static ChatBot bot = new ChatBot();

    public static void run(Message m) {
        bot.sendMsg(m.getChatId(), NewUser.reg(m.getText(), m.getChatId(), userID, null));
        userID++;

        //???????? анкета

    }
}
