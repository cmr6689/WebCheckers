package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for BoardHandler
 */
@Tag("UI-tier")
public class BoardHandlerTest {

    private BoardHandler CuT;
    private Game game;
    private PlayerLobby playerLobby;
    private BoardView board;

    /**
     * setup before running each test
     */
    @BeforeEach
    public void testSetup() {
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i, Piece.COLOR.RED));
        }
        playerLobby = new PlayerLobby(new GameCenter());
        game = playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));
        board = game.getBoardView1();
        CuT = new BoardHandler(board);
    }

    /**
     * test the constructor
     */
    @Test
    public void ctor(){
        assertNotNull(CuT, "The Constructor is Null and should not be");
    }

    /**
     * test updating the board
     */
    @Test
    public void set_board(){
        board.setFinalPos(new Position(0, 0));
        Position end = board.getFinalPos();
        board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
    }

    /**
     * test updating the board with pre-conditions
     */
    @Test
    public void testSetBoardCond() {
        Position start = new Position(1,0);
        Position end = new Position(0, 1);
        game.getBoardView1().getRowAtIndex(1).getSpaceAtIndex(0).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
        game.getBoardView1().setOriginalPos(start);
        game.getBoardView1().setOriginalPos(end);
        CuT.setBoardCond(start, end);
        assertEquals(new Piece(Piece.COLOR.RED, Piece.TYPE.KING), game.getBoardView1().getRowAtIndex(0).getSpaceAtIndex(1).getPiece());

        Position start2 = new Position(6,7);
        Position end2 = new Position(7, 6);
        game.getBoardView1().getRowAtIndex(6).getSpaceAtIndex(7).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
        game.getBoardView1().setOriginalPos(start);
        game.getBoardView1().setOriginalPos(end);
        CuT.setBoardCond(start2, end2);
        assertEquals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING), game.getBoardView1().getRowAtIndex(7).getSpaceAtIndex(6).getPiece());

        game.getBoardView1().setRemovedPiece(new Position(7, 6));
        CuT.setBoardCond(end2, end2);
        assertNull(game.getBoardView1().getRowAtIndex(7).getSpaceAtIndex(6).getPiece());
    }

}
