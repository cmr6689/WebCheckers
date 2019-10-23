package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for BoardView
 * @author Matthew Klein
 */

@Tag("Model-tier")
public class BoardViewTest {

    /**
     * Component under test (CuT)
     */
    private BoardView CuT;
    private ArrayList<Row> rows;

    /**
     * Test if the player isn't 1 and the board is less than 4
     */
   @Test
   public void ctor_case_1(){
       CuT = new BoardView(0);
       CuT.i = 3;
       //ensure the correct Piece is in the correct Space
       assertEquals(CuT.currentColor, Piece.COLOR.WHITE);
   }

    /**
     * Test if the player is 1 and the board is less than 4
     */
    @Test
    public void ctor_case_2(){
        CuT = new BoardView(1);
        CuT.i = 3;
        //ensure the correct Piece is in the correct Space
        assertEquals(CuT.currentColor, Piece.COLOR.RED);
    }

    /**
     * Test if the player isn't 1 and the board is greater than 4
     */
    @Test
    public void ctor_case_3(){
        CuT = new BoardView(0);
        CuT.i = 6;
        //ensure the correct Piece is in the correct Space
        assertEquals(CuT.currentColor, Piece.COLOR.WHITE);
    }

    /**
     * Test if the player is 1 and the board is greater than 4
     */
    @Test
    public void ctor_case_4(){
        CuT = new BoardView(1);
        CuT.i = 6;
        //ensure the correct Piece is in the correct Space
        assertEquals(CuT.currentColor, Piece.COLOR.RED);
    }

    /**
     * Test if the the Board can iterate over Spaces
     */
    @Test
    public void iterator(){
        CuT = new BoardView(0);
        rows = CuT.getRows();
        //Check if the iterable row contains spaces
        assertNotNull(rows, "Their must be rows within the board view to iterate over");
    }

}
