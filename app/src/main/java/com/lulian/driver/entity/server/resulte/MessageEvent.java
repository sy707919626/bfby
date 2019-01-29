package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/8/13.
 */

public class MessageEvent {
    private String message;
    private int msgState;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(int msgState) {
        this.msgState = msgState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMsgState() {
        return msgState;
    }

    public void setMsgState(int msgState) {
        this.msgState = msgState;
    }
}
