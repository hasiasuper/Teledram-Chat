package com.linskyi.objects;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class BotProperties {
    private String botUsername;
    private String botToken;
    private String botHost;
    private int botPort;
    private String botDBName;
}
