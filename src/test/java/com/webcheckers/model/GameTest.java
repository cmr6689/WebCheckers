package com.webcheckers.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Types;
import com.webcheckers.ui.BoardHandler;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Game
 * @author Ronald Torrelli
 * @author Evan Price
 */

@Tag ("Model-tier")
class GameTest {

    /**
     * test the constructor
     */
    @Test
    void ctor_withArg(){
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1 vs 2", CuT.toString());
    }

    /**
     * check makeBoard to see if it's actually adding to rows
     */
    @Test
    void makeBoardTest(){
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getBoard(new Player("1")).getRows());
    }

    /**
     * Tests the getter for the red players board view
     */
    @Test
    public void getBoardView1Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getBoardView1());
        //assertEquals(CuT.getBoardView1(),CuT.getBoardView2());
    }

    /**
     * Tests the getter for the white players board view
     */
    @Test
    public void getBoardView2Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getBoardView2());
        //assertEquals(CuT.getBoardView1(),CuT.getBoardView2());
    }

    /**
     * Test setting player1
     */
    @Test
    void setPlayer1Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        CuT.setPlayer1(new Player("3"));
        assertEquals(new Player("3"), CuT.getPlayer1());
    }

    /**
     * Test setting player2
     */
     @Test
     void setPlayer2Test() {
     final Game CuT = new Game(new Player("1"), new Player("2"));
     CuT.setPlayer2(new Player("4"));
     assertEquals(new Player("4"), CuT.getPlayer2());
     }

    /**
     * Test getting player1
     */
    @Test
    void getPlayer1Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(new Player("1"), CuT.getPlayer1());
    }

    /**
     * Test getting player2
     */
    @Test
    void getPlayer2Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(new Player("2"), CuT.getPlayer2());
    }

    /**
     * test getting player 1 name
     */
    @Test
    void getPlayer1NameTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1", CuT.getPlayer1Name());
    }

    /**
     * test getting player 2 name
     */
    @Test
    void getPlayer2NameTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("2", CuT.getPlayer2Name());
    }

    /**
     * test is game active
     */
    @Test
    void isActiveTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(true, CuT.isActive());
        CuT.setIsActive(false);
        assertEquals(false, CuT.isActive());
    }

    /**
     * test setting if the game is active
     */
    @Test
    void setIsActiveTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(true, CuT.isActive());
        CuT.setIsActive(false);
        assertEquals(false, CuT.isActive());
        CuT.setIsActive(true);
        assertEquals(true, CuT.isActive());
    }

    /**
     * test the removePlayers method
     */
    @Test
    void removePlayersTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getPlayer1());
        assertNotNull(CuT.getPlayer2());
        CuT.removePlayers();
        assertNull(CuT.getPlayer1());
        assertNull(CuT.getPlayer2());
    }

    @Test
    void removeSinglePlayerTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getPlayer1());
        assertNotNull(CuT.getPlayer2());
        CuT.removePlayer1();
        assertNull(CuT.getPlayer1());
        CuT.removePlayer2();
        assertNull(CuT.getPlayer2());
    }

    /**
     * test setting the player
     */
    @Test
    void setPlayerTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        CuT.removePlayers();
        CuT.setPlayer(new Player("1"));
        assertEquals(new Player("1"),CuT.getPlayer1());
        final Game CuT2 = new Game(new Player("2"), new Player("1"));
        CuT2.setPlayer(new Player("4"));
        assertEquals(new Player("4"),CuT2.getPlayer2());
    }

    /**
     * test getMap() method
     */
    @Test
    void getMapTest(){
        Map<String, Object> map1 = new HashMap<>();
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(map1,CuT.getMap());
        CuT.getMap().put("Player1", "1");
        CuT.getMap().put("Player2", "2");
        map1.put("Player1", "1");
        map1.put("Player2", "2");
        assertEquals(map1,CuT.getMap());
    }

    /**
     * test getGameTest() method
     */
    @Test
    void getBoardTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(CuT.getBoard(new Player("1")), CuT.getBoardView1());
        assertEquals(CuT.getBoard(new Player("2")), CuT.getBoardView2());
        assertEquals(CuT.getBoard(new Player("5")), CuT.getBoardView1());
    }

    /**
     * test the toString() method
     */
    @Test
    void toStringTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1 vs 2", CuT.toString());
    }

    @Test
    public void testGameOver() {
        final Game CuT = new Game(new Player("1"), new Player("2"));

        removePieces(CuT);
        CuT.getBoardView1().getRowAtIndex(0).getSpaceAtIndex(0).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
        CuT.checkGameOver();
        assertNotNull(CuT.getMap().get("modeOptionsAsJSON"));
        assertEquals("RED", CuT.getMap().get("activeColor"));

        removePieces(CuT);
        CuT.getBoardView1().getRowAtIndex(0).getSpaceAtIndex(0).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
        CuT.checkGameOver();
        assertNotNull(CuT.getMap().get("modeOptionsAsJSON"));
        assertEquals("WHITE", CuT.getMap().get("activeColor"));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) CuT.getBoardView1().getRowAtIndex(0).getSpaceAtIndex(j).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
                else CuT.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
            }
        }
        CuT.checkGameOver();
        assertNotNull(CuT.getMap().get("modeOptionsAsJSON"));
        assertEquals("WHITE", CuT.getMap().get("activeColor"));
    }

    @Test
    void noWhiteMoves() {
        final Game CuT = new Game(new Player("3"), new Player("4"));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1 && j == 2) {
                    CuT.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
                    System.out.println("White Piece: " + new Position(1, 2).helpString());
                }
                else if (i == 6 && j == 1) {
                    System.out.println("Empty: " + new Position(6, 1).helpString());
                }
                else CuT.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
            }
        }
        CuT.getMap().put("activeColor", "WHITE");
        CuT.checkGameOver();
        assertNotNull(CuT.getMap().get("modeOptionsAsJSON"));
        assertEquals("WHITE", CuT.getMap().get("activeColor"));
    }
    
    public void removePieces(Game game) {
        BoardHandler boardHandler = new BoardHandler(game.getBoardView1());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null ) {
                    game.getBoardView1().setOriginalPos(new Position(i, j));
                    game.getBoardView1().setFinalPos(new Position(i, j));
                    game.getBoardView1().setRemovedPiece(new Position(i, j));
                    boardHandler.setBoard();
                }
            }
        }
    }

    @Test
    public void testEquals() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        final Game CuT2 = new Game(new Player("1"), new Player("2"));
        final Game CuT3 = new Game(new Player("3"), new Player("3"));
        assertTrue(CuT.equals(CuT2));
        assertFalse(CuT.equals(CuT3));
        assertFalse(CuT.equals(null));
        assertFalse(CuT.equals(new Player("1")));
    }

    @Test
    public void testHashCode() {
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        final Game CuT = new Game(p1, p2);
        assertEquals(Objects.hash(p1, p2), CuT.hashCode());
    }

    @Test
    public void testGetPlayerColor() {
        Player p1 = new Player("1");
        p1.setColor(Player.Color.RED);
        Player p2 = new Player("2");
        p2.setColor(Player.Color.WHITE);
        final Game CuT = new Game(p1, p2);

        assertEquals(Player.Color.RED, CuT.getPlayerColor(p1));
        assertEquals(Player.Color.WHITE, CuT.getPlayerColor(p2));
    }

    @Test
    public void testNullRows() {
        Player p1 = new Player("1");
        p1.setColor(Player.Color.RED);
        Player p2 = new Player("2");
        p2.setColor(Player.Color.WHITE);
        final Game CuT = new Game(p1, p2);
        assertEquals(CuT.getBoardView1().getRows().get(7), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(6), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(5), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(4), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(3), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(2), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(1), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertEquals(CuT.getBoardView1().getRows().get(0), CuT.checkNonNullRows());
        CuT.getBoardView1().getRows().remove(CuT.getBoardView1().getRows().size()-1);
        assertNull(CuT.checkNonNullRows());
    }
}