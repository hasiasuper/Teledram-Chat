package com.linskyi;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String nickname;
    private Long id;
    private int chatID;
    private boolean sex;
    private String group;
    private Date dateOfReg;

    private String action;
    private String room;

    private String mood;
    private String maritalStatus;

    private String name;
    private String age;
    private String country;
    private String city;
    private String aboutMe;

    public User(Long id) {
        this.id = id;
    }

}
