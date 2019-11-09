package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

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
    private GameData gameData;

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
        gameData = new GameData();
        CuT = new PostValidateMoveRoute(playerLobby);
    }

    /**
     * Ensure that the fields are initialized
     */
    @Test
    public void ctor() {
        assertNotNull(playerLobby, "PlayerLobby should be initialized.");
        assertNotNull(gameData, "GameData should not be null");
    }

    /**
     * Test whether or not the position is valid
     */
    @Test
    public void positionIsValid() {
        BoardView board = new BoardView(null, null);
        int row = 3;
        int cell = 2;
        assertTrue(CuT.positionIsValid(board, row, cell));
    }

    /**
     * Test whether or not the position is invalid
     */
    @Test
    public void positionIsInvalid() {
        BoardView board = new BoardView(null, null);
        int row = 3;
        int cell = 1;
        assertFalse(CuT.positionIsValid(board, row, cell));
    }
    /**
     * Test whether or not the move is valid
     */
    @Test
    public void moveIsValid() {
        Position start = new Position(6,1);
        Position end = new Position(5, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        //assertTrue(CuT.moveIsValid(move, piece));
    }

    /**
     * Test whether or not the move is invalid
     */
    @Test
    public void moveIsInvalid() {
        Position start = new Position(1,1);
        Position end = new Position(3, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        //assertFalse(CuT.moveIsValid(move, piece));
    }

    /**
     * Test whether or not the jump is valid
     */
    /*@Test
    public void jumpIsValid() {
        //TODO
        Position start = new Position(6,1);
        Position end = new Position(5, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        assertTrue(CuT.jumpIsValid(false, 1, move, new BoardView(), piece, 4, 4));
    }*/

    /**
     * Test whether or not the jump is invalid
     */
    /*@Test
    public void jumpIsInvalid() {
        Position start = new Position(6,1);
        Position end = new Position(5, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        assertFalse(CuT.jumpIsValid(true, 1, move, new BoardView(), piece, 4, 4));
    }*/

    /**
     * Test the handle method by giving a valid move
     */
    @Test
    public void testValidation() {
        Position start = new Position(6,1);
        Position end = new Position(5, 2);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);

        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Your move is valid");

        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));
        when(session.attribute("player")).thenReturn(new Player("Player"));

        playerLobby.addPlayer(new Player("Player"));
        playerLobby.addPlayer(new Player("Opp"));
        playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));
        //playerLobby.setGame(new Game(new Player("Player"), new Player("Opp")));
        assertEquals(gson.toJson(message), CuT.handle(request, response));

    }
}