package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Game
 * @author Ronald Torrelli
 */

@Tag ("Model-tier")
class GameTest {

    @Test
    void ctor_withArg(){
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1 vs 2", CuT.toString());
    }

    /**
     * Test setting player1
     */
    @Test
    void setPlayer1() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        CuT.setPlayer1(new Player("3"));
        assertEquals(new Player("3"), CuT.getPlayer1());
    }

    /**
     * Test setting player2
     /

    /**
     * Test getting player1
     */
    @Test
    void getPlayer1() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(new Player("1"), CuT.getPlayer1());
    }

    /**
     * test getting player 1 name
     */
    @Test
    void getPlayer1Name() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("1", CuT.getPlayer1Name());
    }

    /**
     * test getting player 2 name
     */
    @Test
    void getPlayer2Name() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals("2", CuT.getPlayer2Name());
    }

    /**
     * test is game active
     */
    @Test
    void isActive() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        assertEquals(true, CuT.isActive());
    }

    /**
     * test setting if the game is active
     */
    @Test
    void setIsActive() {
        final Game CuT = new Game(new Player("1"), new Player("2"));
        CuT.setIsActive(false);
        assertEquals(false, CuT.isActive());
    }

}