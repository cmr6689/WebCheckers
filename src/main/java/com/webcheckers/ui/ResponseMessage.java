package com.webcheckers.ui;

/**
 * This class handles the ajax json responses by setting the text and the type of the response
 *
 * @author Team-E
 */
public class ResponseMessage {
    //message text
    private String text;
    //message type is either error or info
    public enum MessageType {ERROR, INFO};
    //the message type
    private MessageType type;

    /**
     * Set the text of the json message
     * @param text the response text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter of the message text
     * @return message text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the type of the message
     * @param type error or info
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Getter for the type of message
     * @return the type
     */
    public MessageType gettype() {
        return type;
    }
}