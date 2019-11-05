package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
     * Tests the getter for the red players board view
     */
    @Test
    public void getBoardView1Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getBoardView1());
        assertEquals(CuT.getBoardView1(),CuT.getBoardView2());
    }

    /**
     * Tests the getter for the white players board view
     */
    @Test
    public void getBoardView2Test() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertNotNull(CuT.getBoardView2());
        assertEquals(CuT.getBoardView1(),CuT.getBoardView2());
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
     * test setting the player
     */
    @Test
    void setPlayerTest() {
        final Game CuT = new Game(null, new Player("2"));
        assertEquals(null,CuT.getPlayer1());
        CuT.setPlayer(new Player("1"));
        assertEquals(new Player("1"),CuT.getPlayer1());
        final Game CuT1 = new Game(new Player("2"), null);
        CuT1.setPlayer(new Player("1"));
        assertEquals(new Player("1"),CuT1.getPlayer2());
        final Game CuT2 = new Game(new Player("2"), new Player("1"));
        CuT2.setPlayer(new Player("4"));
        assertEquals(new Player("4"),CuT2.getPlayer2());
    }

    /**
     * test getGameTest() method
     */
    @Test
    void getGameTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(CuT.getGame(new Player("1")), CuT.getBoardView1());
        assertEquals(CuT.getGame(new Player("2")), CuT.getBoardView2());
        assertEquals(CuT.getGame(new Player("5")), CuT.getBoardView1());
    }

    /**
     * test the toString() method
     */
    @Test
    void toStringTest() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1 vs 2", CuT.toString());
    }
}