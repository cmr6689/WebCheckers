package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostValidateMoveRoute
 * @author Cameron Riu
 */
@Tag("Ui-tier")
public class PostHelpMoveRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostHelpMoveRoute CuT;
    private PlayerLobby playerLobby;
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
        playerLobby = new PlayerLobby(new GameCenter());
        CuT = new PostHelpMoveRoute(playerLobby);
    }

    /**
     * Ensure that the fields are initialized
     */
    @Test
    public void ctor() {
        assertNotNull(playerLobby, "PlayerLobby should be initialized.");
    }

    @Test
    public void testRedMoveHelp() {
        when(session.attribute("player")).thenReturn(new Player("Player"));
        playerLobby.addPlayer(new Player("Player"));
        playerLobby.addPlayer(new Player("Opp"));
        playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));

        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("(0,2) -> (1,3)");

        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }

    @Test
    public void testWhiteMoveHelp() {
        when(session.attribute("player")).thenReturn(new Player("Player"));
        playerLobby.addPlayer(new Player("Player"));
        playerLobby.addPlayer(new Player("Opp"));
        playerLobby.getGameCenter().newGame(new Player("Opp"), new Player("Player"));

        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("(6,2) -> (7,3)");

        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }

}
