package com.webcheckers.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for ResponseMessage
 *
 * @author - Cameron Riu
 */
@Tag("UI-tier")
public class ResponseMessageTest {

    /**
     * Test that when a response message is created its values are null
     */
    @Test
    public void testConstructor() {
        ResponseMessage message = new ResponseMessage();
        assertNull(message.getText());
        assertNull((message.gettype()));
    }

    /**
     * Test the type of the response message
     */
    @Test
    public void testType() {
        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        assertEquals(message.gettype(), ResponseMessage.MessageType.INFO);
        message.setType(ResponseMessage.MessageType.ERROR);
        assertEquals(message.gettype(), ResponseMessage.MessageType.ERROR);
    }

    /**
     * Test the text of the response message
     */
    @Test
    public void testMessage() {
        ResponseMessage message = new ResponseMessage();
        message.setText("TEXT");
        assertEquals(message.getText(), "TEXT");
    }
}
