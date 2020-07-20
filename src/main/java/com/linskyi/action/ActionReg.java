package com.linskyi.action;

import com.linskyi.NewUser;
import org.telegram.telegrambots.api.objects.Message;

import static com.linskyi.CreateChatBotRun.bot;

public class ActionReg {

    public static void run(Message m) {
        bot.sendMsg(m.getChatId(), NewUser.reg(m.getText(), m.getChatId()));

        //заполнение анкеты

    }
}
