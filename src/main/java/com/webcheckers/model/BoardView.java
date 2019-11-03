package com.webcheckers.model;
import com.webcheckers.model.Piece.COLOR;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The BoardView class controls how the board is viewed by each player
 * It sends different board views for player one and for player two
 *
 * @author Team-E
 */
public class BoardView implements Iterable{
    //create a new ArrayList of Rows
    private ArrayList<Row> rows = new ArrayList<>();
    //the current color
    COLOR currentColor;
    int i;

    /**
     * Constructor for the board view that creates the orientation
     * of the board based on the color that the player is
     * @param player player 1 (red) or player 2 (white)
     */
    public BoardView(int player){
        if(player == 1) {
            for (i = 0; i < 8; i++) {
                //create a new space
                COLOR color;
                if (i < 4) {
                    color = COLOR.WHITE;
                    currentColor = color;
                } else {
                    color = COLOR.RED;
                    currentColor = color;
                }
                Row row = new Row(i, color);
                if ((i % 2) == 0) {
                    row = new Row(i, color);
                }
                //add that space to the ArrayList
                rows.add(row);
            }
        }else{
            for (i = 0; i < 8; i++) {
                //create a new space
                COLOR color;
                if (i < 4) {
                    color = COLOR.RED;
                    currentColor = color;
                } else {
                    color = COLOR.WHITE;
                    currentColor = color;
                }
                Row row = new Row(i, color);
                if ((i % 2) == 0) {
                    row = new Row(i, color);
                }
                //add that space to the ArrayList
                rows.add(row);
            }
        }
    }


    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    /**
     * getter for the array list of rows created
     * @return the list of rows
     */
    public ArrayList<Row> getRows(){
        return rows;
    }

    /**
     * Method to retrieve a specific row from the list of rows
     * @param index the row number
     * @return the row
     */
    public Row getRowAtIndex(int index){
        return this.rows.get(index);
    }
}
