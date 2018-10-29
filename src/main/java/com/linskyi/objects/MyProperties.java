package com.linskyi.objects;

import lombok.Data;

@Data
public class MyProperties {
    private String botUsername;
    private String botToken;

    public MyProperties(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }
}
