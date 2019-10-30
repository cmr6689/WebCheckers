package com.webcheckers.ui;

public class ResponseMessage {
    private String text;
    public enum MessageType {ERROR, INFO};
    private MessageType type;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageType gettype() {
        return type;
    }
}