package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Piece
 * @author Evan Price
 */

@Tag ("Model-tier")
class PieceTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg(){
        final Piece CuT = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        assertEquals("This piece is a KING and has color RED", CuT.toString());
    }

    /**
     * Test getting the type of a piece
     */
    @Test
    void getTypeTest() {
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        final Piece CuT2 = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        assertEquals(Piece.TYPE.KING, CuT1.getType());
        assertEquals(Piece.TYPE.SINGLE, CuT2.getType());
    }

    /**
     * Test setting the type of a piece
     */
    @Test
    void setTypeTest() {
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        CuT1.setType(Piece.TYPE.SINGLE);
        assertEquals(Piece.TYPE.SINGLE, CuT1.getType());
        CuT1.setType(Piece.TYPE.KING);
        assertEquals(Piece.TYPE.KING, CuT1.getType());
    }

    /**
     * Test getting the color of a piece
     */
    @Test
    void getColorTest() {
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        final Piece CuT2 = new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING);
        assertEquals(Piece.COLOR.RED, CuT1.getColor());
        assertEquals(Piece.COLOR.WHITE, CuT2.getColor());
    }

    /**
     * Test setting the color of a piece
     */
    @Test
    void setColorTest() {
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        CuT1.setColor(Piece.COLOR.WHITE);
        assertEquals(Piece.COLOR.WHITE, CuT1.getColor());
        CuT1.setColor(Piece.COLOR.RED);
        assertEquals(Piece.COLOR.RED, CuT1.getColor());
    }

    /**
     * Test kinging a piece
     */
    @Test
    void kingPieceTest() {
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        assertNotEquals(Piece.TYPE.KING, CuT1.getType());
        CuT1.kingPiece();
        assertEquals(Piece.TYPE.KING, CuT1.getType());
    }

    /**
     * test the overridden equals() method
     */
    @Test
    void equalsTest() {
        final Piece CuT = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        final Piece CuT2 = new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING);
        final Piece CuT1 = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        assertFalse(CuT1.equals(CuT2));
        assertTrue(CuT.equals(CuT1));
    }

    /**
     * test the overridden toString() method
     */
    @Test
    void toStringTest() {
        final Piece CuT = new Piece(Piece.COLOR.RED, Piece.TYPE.KING);
        final Piece CuT1 = new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE);
        assertEquals("This piece is a KING and has color RED", CuT.toString());
        assertNotEquals("This piece is a KING and has color WHITE", CuT1.toString());
    }
}