package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Move
 * @author Evan Price
 */

@Tag ("Model-tier")
class MoveTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg(){
        final Move CuT = new Move(new Position(3,2), new Position(4,3));
        assertEquals("Starting at position 3:2 and trying to move to position 4:3", CuT.toString());
    }

     /**
     * Test getting a start position
     */
    @Test
    void getStartPosTest() {
        final Move CuT = new Move(new Position(3,2), new Position(4,3));
        assertEquals(new Position(3,2), CuT.getStart());
    }

    /**
     * test getting an end position
     */
    @Test
    void getEndPosTest() {
        final Move CuT = new Move(new Position(3,2), new Position(4,3));
        assertEquals(new Position(4,3), CuT.getEnd());
    }

    /**
     * test the overridden equals() method
     */
    @Test
    void equalsTest() {
        final Move CuT1 = new Move(new Position(3,2), new Position(4,3));
        final Move CuT2 = new Move(new Position(3,3), new Position(4,3));
        final Move CuT = new Move(new Position(3,2), new Position(4,3));
        assertFalse(CuT1.equals(CuT2));
        assertTrue(CuT.equals(CuT1));
        assertTrue(CuT.equals(CuT));
        assertFalse(CuT.equals("Hello"));
    }

    /**
     * test the overridden toString() method
     */
    @Test
    void toStringTest() {
        final Move CuT = new Move(new Position(3,2), new Position(4,3));
        final Move CuT1 = new Move(new Position(3,3), new Position(4,3));
        assertEquals("Starting at position 3:2 and trying to move to position 4:3", CuT.toString());
        assertNotEquals("Starting at position 3:2 and trying to move to position 4:3", CuT1.toString());
    }
}