package com.linskyi.objects;

import lombok.Data;

@Data
public class BotProperties {
    private String botUsername;
    private String botToken;
    private String botHost;
    private int botPort;
    private String botDBName;

    //todo можна замінити на AllArgsConstructor анотацію з ломбока
    public BotProperties(String botUsername, String botToken, String botHost, int botPort, String botDBName) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botHost = botHost;
        this.botPort = botPort;
        this.botDBName = botDBName;
    }
}
