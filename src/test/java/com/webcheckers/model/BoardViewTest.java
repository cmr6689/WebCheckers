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
    private Player player = new Player("1");
    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Row> otherBoard = new ArrayList<>();

    /**
     * Test if the the Board can iterate over Spaces
     */
    @Test
    void iterator(){
        player.setColor(Player.Color.RED);
        CuT = new BoardView(rows,player);
        //Check if the iterable row contains spaces
        assertNotNull(CuT.iterator());
    }

    /**
     * Test increaseNumMoves
     */
    @Test
    void increaseNumMovsTest(){
        player.setColor(Player.Color.RED);
        BoardView CuT = new BoardView(rows,player);
        assertEquals(0,CuT.getNumMovs());
        CuT.increaseNumMovs();
        assertEquals(1,CuT.getNumMovs());
    }

    /**
     * Test setRows()
     */
    @Test
    void setRowsTest(){
        ArrayList<Row> newRows = new ArrayList<>();
        newRows.add(new Row(1, Piece.COLOR.RED));
        player.setColor(Player.Color.RED);
        BoardView CuT = new BoardView(rows,player);
        assertEquals(new ArrayList<Row>(), CuT.getRows());
        CuT.setRows(newRows);
        assertEquals(newRows,CuT.getRows());
    }
}
