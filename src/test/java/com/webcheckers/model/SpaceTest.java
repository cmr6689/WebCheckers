package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Space
 * @author Cameron Riu
 */

@Tag ("Model-tier")
class SpaceTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg() {
        final Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        final Space CuT = new Space(piece, 1, Space.BOARD_COLOR.BLACK);
        assertEquals("The piece on this space is This piece is a SINGLE and has color RED, the index of this space is 1 and, the color of this space is BLACK", CuT.toString());
    }

    /**
     * Test the second constructor
     */
    @Test
    void ctor2_withArg() {
        Position pos = new Position(4,3);
        final Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        final Space CuT = new Space(piece, 1, Space.BOARD_COLOR.BLACK, pos);
        assertEquals("The piece on this space is This piece is a SINGLE and has color RED, the index of this space is 1 and, the color of this space is BLACK", CuT.toString());
    }

    /**
     * test the set piece at space method
     */
    @Test
    void setGetPieceTest() {
        final Piece piece = new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE);
        final Space CuT = new Space(null, 1, Space.BOARD_COLOR.BLACK);
        assertNull(CuT.getPiece());
        CuT.setPiece(piece);
        assertEquals(piece, CuT.getPiece());
    }

    /**
     * test the board color of a space
     */
    @Test
    void getIndexandBoardColorTest() {
        final Space CuT = new Space(null, 1, Space.BOARD_COLOR.WHITE);
        assertEquals(1, CuT.getCellIdx());
        assertEquals(Space.BOARD_COLOR.WHITE, CuT.getBoardColor());
    }

    /**
     * Test the getter for Position
     */
    @Test
    void getPositionTest() {
        Position pos = new Position(4,3);
        final Piece piece = new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE);
        final Space CuT = new Space(piece, 1, Space.BOARD_COLOR.BLACK, pos);
        assertEquals(pos, CuT.getPosition());
    }

    /**
     * test if a space is valid
     */
    @Test
    void isValidTest() {
        final Piece piece = new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE);
        final Space CuT = new Space(null, 1, Space.BOARD_COLOR.BLACK);
        assertTrue(CuT.isValid());
        CuT.setPiece(piece);
        assertFalse(CuT.isValid());
    }
}