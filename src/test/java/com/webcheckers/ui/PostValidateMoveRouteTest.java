package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostValidateMoveRoute
 * @author Cameron Riu
 */
@Tag("Ui-tier")
public class PostValidateMoveRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostValidateMoveRoute CuT;
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
        CuT = new PostValidateMoveRoute(playerLobby);
    }

    /**
     * Ensure that the fields are initialized
     */
    @Test
    public void ctor() {
        assertNotNull(playerLobby, "PlayerLobby should be initialized.");
    }

    /**
     * Test the handle method by giving a valid move
     */
    @Test
    public void testInvalidation() {
        Position start = new Position(6,1);
        Position end = new Position(5, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);

        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.ERROR);
        message.setText("Your move is not valid");

        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));
        when(session.attribute("player")).thenReturn(new Player("Player"));

        playerLobby.addPlayer(new Player("Player"));
        playerLobby.addPlayer(new Player("Opp"));
        playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));
        //playerLobby.setGame(new Game(new Player("Player"), new Player("Opp")));
        assertEquals(gson.toJson(message), CuT.handle(request, response));

    }
}