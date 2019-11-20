package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveHelperTest {
    /**
     * Component under test (CuT)
     */
    private MoveHelper CuT;
    private PlayerLobby playerLobby;
    private Session session;
    private Request request;
    private Response response;
    private Game game;

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
        game = playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));
        CuT = new MoveHelper(game);
    }

    /**
     * Ensure that the fields are initialized
     */
    @Test
    public void ctor() {
        assertNotNull(game, "Game should be initialized.");
    }

    @Test
    public void testRedMove() {
        MoveChecks moveChecks = new MoveChecks(game);
        String start = moveChecks.checkSinglePieceMoves(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE), new Position(5, 0)).get(0).getStart().helpString();
        String end = moveChecks.checkSinglePieceMoves(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE), new Position(5, 0)).get(0).getEnd().helpString();
        assertEquals(start + " -> " + end, CuT.getRedMove());
        assertEquals("nope", CuT.getRedJump());
    }

    @Test
    public void testWhiteMove() {
        MoveChecks moveChecks = new MoveChecks(game);
        String start = moveChecks.checkSinglePieceMoves(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE), new Position(2, 1)).get(0).getStart().helpString2();
        String end = moveChecks.checkSinglePieceMoves(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE), new Position(2, 1)).get(0).getEnd().helpString2();
        assertEquals(start + " -> " + end, CuT.getWhiteMove());
        assertEquals("nope", CuT.getWhiteJump());
    }

    @Test
    public void testRedJump() {
        BoardHandler handler = new BoardHandler(game.getBoardView1());
        game.getBoardView1().setOriginalPos(new Position(5, 2));
        game.getBoardView1().setFinalPos(new Position(4, 3));
        game.getBoardView1().setMoveThisTurn(new Move(new Position(5, 2), new Position(4, 3)));
        handler.setBoard();

        game.getBoardView1().setOriginalPos(new Position(2, 5));
        game.getBoardView1().setFinalPos(new Position(3, 4));
        game.getBoardView1().setMoveThisTurn(new Move(new Position(2, 5), new Position(3, 4)));
        handler.setBoard();

        MoveChecks moveChecks = new MoveChecks(game);
        String start = moveChecks.checkSinglePieceJumps(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE), new Position(4, 3)).get(0).getStart().helpString();
        String end = moveChecks.checkSinglePieceJumps(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE), new Position(4, 3)).get(0).getEnd().helpString();
        assertEquals(start + " -> " + end, CuT.getRedJump());
    }

    @Test
    public void testWhiteJump() {
        BoardHandler handler = new BoardHandler(game.getBoardView1());
        game.getBoardView1().setOriginalPos(new Position(5, 2));
        game.getBoardView1().setFinalPos(new Position(4, 3));
        game.getBoardView1().setMoveThisTurn(new Move(new Position(5, 2), new Position(4, 3)));
        handler.setBoard();

        game.getBoardView1().setOriginalPos(new Position(2, 5));
        game.getBoardView1().setFinalPos(new Position(3, 4));
        game.getBoardView1().setMoveThisTurn(new Move(new Position(2, 5), new Position(3, 4)));
        handler.setBoard();

        MoveChecks moveChecks = new MoveChecks(game);
        String start = moveChecks.checkSinglePieceJumps(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE), new Position(3, 4)).get(0).getStart().helpString2();
        String end = moveChecks.checkSinglePieceJumps(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE), new Position(3, 4)).get(0).getEnd().helpString2();
        assertEquals(start + " -> " + end, CuT.getWhiteJump());
    }
}
