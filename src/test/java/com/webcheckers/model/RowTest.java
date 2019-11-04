package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for Row
 * @author Evan Price
 */

@Tag ("Model-tier")
class RowTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg() {
        //test row 0
        for(int index = 0; index < 8; index++) {
            final Row CuT0 = new Row(index, Piece.COLOR.WHITE);
            if(index % 2 == 0) {
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 0) {
                        assertTrue(CuT0.getSpaceAtIndex(i).getBoardColor() == Space.BOARD_COLOR.WHITE);
                        assertEquals("The piece on this space is null, the index of this space is " + i + " and, the color of this space is WHITE", CuT0.getSpaceAtIndex(i).toString());
                        assertNull(CuT0.getSpaceAtIndex(i).getPiece());
                    } else {
                        assertTrue(CuT0.getSpaceAtIndex(i).getBoardColor() == Space.BOARD_COLOR.BLACK);
                        if((index < 2   || index > 5)) {
                            assertNotNull(CuT0.getSpaceAtIndex(i).getPiece());
                            assertEquals("The piece on this space is " + CuT0.getSpaceAtIndex(i).getPiece().toString() + ", the index of this space is " + i + " and, the color of this space is BLACK", CuT0.getSpaceAtIndex(i).toString());
                        }
                    }
                    assertNotNull(CuT0.getSpaceAtIndex(i));
                }
            }else{
                for (int i = 0; i < 8; i++) {
                    if (i % 2 == 1) {
                        assertTrue(CuT0.getSpaceAtIndex(i).getBoardColor() == Space.BOARD_COLOR.WHITE);
                        assertEquals("The piece on this space is null, the index of this space is " + i + " and, the color of this space is WHITE", CuT0.getSpaceAtIndex(i).toString());
                        assertNull(CuT0.getSpaceAtIndex(i).getPiece());
                    } else {
                        assertTrue(CuT0.getSpaceAtIndex(i).getBoardColor() == Space.BOARD_COLOR.BLACK);
                        if((index < 2   || index > 5)) {
                            assertNotNull(CuT0.getSpaceAtIndex(i).getPiece());
                            assertEquals("The piece on this space is " + CuT0.getSpaceAtIndex(i).getPiece().toString() + ", the index of this space is " + i + " and, the color of this space is BLACK", CuT0.getSpaceAtIndex(i).toString());
                        }
                    }
                    assertNotNull(CuT0.getSpaceAtIndex(i));
                }
            }
        }
    }

    /**
     * Test the getIndex method
     */
    @Test
    public void getIndexTest(){
        final Row CuT = new Row(1, Piece.COLOR.WHITE);
        assertEquals(1,CuT.getIndex());
    }

    /**
     * Test the getBoardColor method
     */
    @Test
    public void getBoardColorTest(){
        for(int i = 0; i < 8; i++) {
            final Row CuT = new Row(i, Piece.COLOR.WHITE);
            if(i % 2 == 0) {
                assertEquals(Space.BOARD_COLOR.BLACK, CuT.getBoardColor());
            }else{
                assertEquals(Space.BOARD_COLOR.WHITE, CuT.getBoardColor());
            }
        }
    }

    /**
     * Test the getSpaceAtIndex method
     * shows that a row is successfully created with spaces and pieces
     */
    @Test
    public void getSpaceAtIndexTest(){
        final Row CuT = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is This piece is a SINGLE and has color WHITE, the index of this space is 0 and, the color of this space is BLACK",CuT.getSpaceAtIndex(0).toString());
        final Row CuT1 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is null, the index of this space is 1 and, the color of this space is WHITE",CuT.getSpaceAtIndex(1).toString());
        final Row CuT2 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is This piece is a SINGLE and has color WHITE, the index of this space is 2 and, the color of this space is BLACK",CuT.getSpaceAtIndex(2).toString());
        final Row CuT3 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is null, the index of this space is 3 and, the color of this space is WHITE",CuT.getSpaceAtIndex(3).toString());
        final Row CuT4 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is This piece is a SINGLE and has color WHITE, the index of this space is 4 and, the color of this space is BLACK",CuT.getSpaceAtIndex(4).toString());
        final Row CuT5 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is null, the index of this space is 5 and, the color of this space is WHITE",CuT.getSpaceAtIndex(5).toString());
        final Row CuT6 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is This piece is a SINGLE and has color WHITE, the index of this space is 6 and, the color of this space is BLACK",CuT.getSpaceAtIndex(6).toString());
        final Row CuT7 = new Row(1, Piece.COLOR.WHITE);
        assertEquals("The piece on this space is null, the index of this space is 7 and, the color of this space is WHITE",CuT.getSpaceAtIndex(7).toString());
    }
}