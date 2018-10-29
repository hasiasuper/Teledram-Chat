package com.linskyi;

import static com.linskyi.ChatBotRun.listUsers;

public class NewUser {

    public static String reg(String nickname, Long id, int chatID, String status) {

        listUsers.get(id).setNickname(nickname);
        listUsers.get(id).setChatID(chatID);
        listUsers.get(id).setAction(status);
        return "Поздравляем, " + nickname + ", вы успешно зарегистрированы! Ваш id в чате [" + chatID + "]. Теперь вы можете войти в чат - /chat";
    }

}
