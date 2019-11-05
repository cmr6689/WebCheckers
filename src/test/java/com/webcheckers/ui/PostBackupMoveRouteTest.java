package com.webcheckers.ui;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostBackupMoveRoute
 * @author Cameron Riu
 */
@Tag("Ui-tier")
public class PostBackupMoveRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostBackupMoveRoute CuT;
    private Session session;
    private Request request;
    private Response response;

    /**
     * Setup mock classes to fill dependencies through
     * the test seam
     */
    @BeforeEach
    public void testSetup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        CuT = new PostBackupMoveRoute();
    }

    /**
     * Test if the message returned is accurate
     */
    @Test
    public void testMessage() {
        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Your move has been backed up");

        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }
}