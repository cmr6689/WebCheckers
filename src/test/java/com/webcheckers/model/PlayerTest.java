package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Player
 * @author Evan Price
 */

@Tag ("Model-tier")
class PlayerTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg() {
        final Player CuT = new Player("1");
        assertEquals("The name of this player is 1 and their color is null", CuT.toString());
    }

    /**
     * Test getting the name of a player
     */
    @Test
    void getNameTest() {
        final Player CuT = new Player("Hi");
        assertEquals("Hi", CuT.getName());
        assertNotEquals("null", CuT.getName());
    }

    /**
     * Test setting the name of a player
     */
    @Test
    void setNameTest() {
        final Player CuT = new Player("Hi");
        assertEquals("Hi", CuT.getName());
        CuT.setName("Hello");
        assertNotEquals("Hi", CuT.getName());
        assertEquals("Hello", CuT.getName());
    }

    /**
     * Test getting the color of a player
     */
    @Test
    void getColorTest() {
        final Player CuT1 = new Player("HI");
        final Player CuT2 = new Player("hi");
        assertNotEquals(Player.Color.RED, CuT1.getColor());
        assertNotEquals(Player.Color.WHITE, CuT2.getColor());
        CuT1.setColor(Player.Color.RED);
        assertEquals(Player.Color.RED, CuT1.getColor());
        CuT2.setColor(Player.Color.WHITE);
        assertEquals(Player.Color.WHITE, CuT2.getColor());
    }

    /**
     * Test setting the color of a player
     */
    @Test
    void setColorTest() {
        final Player CuT1 = new Player("HI");
        final Player CuT2 = new Player("hi");
        assertNotEquals(Player.Color.RED, CuT1.getColor());
        CuT1.setColor(Player.Color.RED);
        assertEquals(Player.Color.RED, CuT1.getColor());
        assertNotEquals(Player.Color.WHITE, CuT2.getColor());
        CuT2.setColor(Player.Color.WHITE);
        assertEquals(Player.Color.WHITE, CuT2.getColor());
    }

    /**
     * Test getting if a player is in a game
     */
    @Test
    void inGameTest() {
        final Player CuT1 = new Player("Hi");
        assertNotEquals(true, CuT1.getInGame());
        CuT1.setInGame(true);
        assertEquals(true, CuT1.getInGame());
    }

    /**
     * Test setting if a player is in a game
     */
    @Test
    void setInGameTest() {
        final Player CuT1 = new Player("Hi");
        assertNotEquals(true, CuT1.getInGame());
        CuT1.setInGame(true);
        assertEquals(true, CuT1.getInGame());
        CuT1.setInGame(false);
        assertEquals(false, CuT1.getInGame());
    }

    /**
     * test the overridden equals() method
     */
    @Test
    void equalsTest() {
        final Player CuT = new Player("HI");
        final Player CuT1 = new Player("HI");
        final Player CuT2 = new Player("hi");
        assertFalse(CuT1.equals(CuT2));
        assertTrue(CuT.equals(CuT1));
    }

    /**
     *
     */
    @Test
    void hashCodeTest() {
        final Player CuT = new Player("HI");
        assertEquals(2305,CuT.hashCode());
    }

    /**
     * test the overridden toString() method
     */
    @Test
    void toStringTest() {
        final Player CuT1 = new Player("HI");
        final Player CuT2 = new Player("hi");
        assertEquals("The name of this player is HI and their color is null", CuT1.toString());
        assertEquals("The name of this player is hi and their color is null", CuT2.toString());
        CuT1.setColor(Player.Color.RED);
        CuT2.setColor(Player.Color.WHITE);
        assertEquals("The name of this player is HI and their color is RED", CuT1.toString());
        assertEquals("The name of this player is hi and their color is WHITE", CuT2.toString());
    }
}