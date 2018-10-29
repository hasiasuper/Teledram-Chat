package com.linskyi;

import java.util.HashMap;
import java.util.Map;

import static com.linskyi.ChatBot.roomID;

public class NewRoom {

    public static Map<Integer, Room> createGeneralRoom()
    {
        Map<Integer,Room> listRooms = new HashMap<>();
        listRooms.put(roomID, new Room(roomID, "Гостинная", "Приятного общения!", new HashMap<>()));
        listRooms.put(2, new Room(2, "Кухня", "Приятного общения!", new HashMap<>())); //временная
        listRooms.put(3, new Room(3, "Ванная", "Приятного общения!", new HashMap<>())); //временная
        return listRooms;
    }


}
