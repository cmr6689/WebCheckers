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
    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Row> otherBoard = new ArrayList<>();

    /**
     * Test if the board is less than 4
     */
   @Test
   public void ctor_case_1(){
       CuT = new BoardView(null, null);
       if(CuT.i < 4) {
           //ensure the correct Piece is in the correct Space
           assertEquals(CuT.currentColor, Piece.COLOR.WHITE);
       }
   }

    /**
     * Test if the board is greater than 4
     */
    @Test
    public void ctor_case_2(){
        CuT = new BoardView(null, null);
        if(CuT.i > 4) {
            //ensure the correct Piece is in the correct Space
            assertEquals(CuT.currentColor, Piece.COLOR.RED);
        }
    }


    /**
     * Test if the the Board can iterate over Spaces
     */
    @Test
    public void iterator(){
        CuT = new BoardView(null, null);
        rows = CuT.getRows();
        //Check if the iterable row contains spaces
        assertNotNull(rows, "Their must be rows within the board view to iterate over");
    }

    @Test
    public void reverse_iterator(){
        CuT = new BoardView(null, null);
        Row one = new Row(0, Piece.COLOR.RED);
        Row two = new Row(1, Piece.COLOR.WHITE);
        Row three = new Row(1, Piece.COLOR.WHITE);
        Row four = new Row(1, Piece.COLOR.WHITE);
        Row five = new Row(1, Piece.COLOR.WHITE);
        Row six = new Row(1, Piece.COLOR.WHITE);
        Row seven = new Row(1, Piece.COLOR.WHITE);
        Row eight = new Row(1, Piece.COLOR.RED);
        //add rows
        rows.add(one);
        rows.add(two);
        rows.add(three);
        rows.add(four);
        rows.add(five);
        rows.add(six);
        rows.add(seven);
        rows.add(eight);
        CuT.setRows(rows);
        //call the reverse iterator
        CuT.reverseIterator();
        //Check if the iterable row contains spaces
        assertNotNull(CuT.getOtherBoard(), "Their must be rows within the board view to iterate over");
        //check that the other board is not the same as the first players board
        assertNotEquals(CuT.getOtherBoard(), rows);
        //check that pieces are placed in reverse
        assertEquals(CuT.getOtherBoard().get(0), eight);
        assertEquals(CuT.getOtherBoard().get(1), seven);
    }

}
