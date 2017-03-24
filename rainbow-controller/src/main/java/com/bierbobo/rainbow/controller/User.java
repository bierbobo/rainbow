package com.bierbobo.rainbow.controller;

/**
 * Created by lifubo on 2017/3/21.
 */
public class User {


    private String username;


    public User( ) {

    }

    public User(String username) {
        this.username = username;
    }

    public User(int i, String user2) {

        this.username=user2;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
