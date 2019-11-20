package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Position
 * @author Evan Price
 */

@Tag ("Model-tier")
class PositionTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg() {
        final Position CuT = new Position(1,2);
        assertEquals("The position of this piece is row 1 and cell 2", CuT.toString());
    }

    /**
     * Test getting the row of a position
     */
    @Test
    void getRowTest() {
        final Position CuT = new Position(1,2);
        assertEquals(1, CuT.getRow());
    }

    /**
     * Test getting the cell of a position
     */
    @Test
    void getCellTest() {
        final Position CuT = new Position(1,2);
        assertEquals(2, CuT.getCell());
    }

    /**
     * test the overridden equals() method
     */
    @Test
    void equalsTest() {
        final Position CuT = new Position(1,2);
        final Position CuT1 = new Position(1,2);
        final Position CuT2 = new Position(4,2);
        final Position CuT3 = new Position(1,3);
        assertFalse(CuT1.equals(CuT2));
        assertFalse(CuT1.equals(CuT3));
        assertTrue(CuT.equals(CuT1));
    }

    /**
     * test the helpString method
     */
    @Test
    void helpStringTest() {
        final Position CuT1 = new Position(1,2);
        final Position CuT2 = new Position(7,5);
        assertEquals("(2,6)", CuT1.helpString());
        assertEquals("(5,0)", CuT2.helpString());
    }

    /**
     * test the helpString2 method
     */
    @Test
    void helpString2Test() {
        final Position CuT1 = new Position(1,2);
        final Position CuT2 = new Position(7,5);
        assertEquals("(5,1)", CuT1.helpString2());
        assertEquals("(2,7)", CuT2.helpString2());
    }

    /**
     * test the overridden toString() method
     */
    @Test
    void toStringTest() {
        final Position CuT1 = new Position(1,2);
        final Position CuT2 = new Position(7,5);
        assertEquals("The position of this piece is row 1 and cell 2", CuT1.toString());
        assertEquals("The position of this piece is row 7 and cell 5", CuT2.toString());
        final Position CuT3 = new Position(6,3);
        final Position CuT4 = new Position(4,0);
        assertEquals("The position of this piece is row 6 and cell 3", CuT3.toString());
        assertEquals("The position of this piece is row 4 and cell 0", CuT4.toString());
    }
}