package com.project.viewtest.model;

/**
 * Created by Administrator on 2018/10/31.
 * 消息model
 */

public class MessageModel {

    private String name;
    private String message;
    private String notRead;

    public MessageModel(String name, String message, String notRead) {
        this.name = name;
        this.message = message;
        this.notRead = notRead;
    }

    public MessageModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotRead() {
        return notRead;
    }

    public void setNotRead(String notRead) {
        this.notRead = notRead;
    }
}
