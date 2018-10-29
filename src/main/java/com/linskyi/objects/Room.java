package com.linskyi.objects;

import lombok.Data;

import java.util.Map;

@Data
public class Room {

    private String titleRoom;
    private int roomID;
    private String description;
    private Map<Long, Long> listOnline;

    public Room(int roomID, String titleRoom, String description, Map<Long, Long> listOnline) {
        this.roomID = roomID;
        this.titleRoom = titleRoom;
        this.description = description;
        this.listOnline = listOnline;
    }
}
