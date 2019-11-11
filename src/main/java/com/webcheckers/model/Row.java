package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import com.webcheckers.model.Piece.COLOR;
import com.webcheckers.model.Space.BOARD_COLOR;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Row class handles the rows of the game board by keeping track
 * of the spaces in each spot and the color of each space
 */
public class Row implements Iterable{

    //integer from 0 to 7 indicating the space in the row
    private int index;
    private BOARD_COLOR boardColor;

    //creates an array list to hold all the spaces in this row
    private ArrayList<Space> spaces = new ArrayList<Space>();

    /**
     * The constructor for the row class that creates the rows
     * @param index the index of the row
     * @param color the color of the pieces
     */
    public Row(int index, COLOR color){
        this.index = index;
        for(int i = 0; i < 8; i++){
            if(index % 2 == 0) {
                if (i % 2 == 0) {
                    this.boardColor = BOARD_COLOR.WHITE;
                } else {
                    this.boardColor = BOARD_COLOR.BLACK;
                }
            }else{
                if (i % 2 == 0) {
                    this.boardColor = BOARD_COLOR.BLACK;
                } else {
                    this.boardColor = BOARD_COLOR.WHITE;
                }
            }
            //create a new space
            Space space = new Space(null, i, this.boardColor);
            //add that space to the ArrayList
            if ((this.boardColor.equals(BOARD_COLOR.BLACK)) && (index < 3  || index > 4)) {
                space.setPiece(new Piece(color, TYPE.SINGLE));
            }
            spaces.add(space);

        }
    }
    //for p2
    public Row(Row row) {
        for (int i = 7; i >= 0; i--) {
            spaces.add(row.getSpaceAtIndex(i));
        }
    }

    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

    /**
     * Getter method for the row index
     * @return The index of this row within the board
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter method for the board color index
     * @return The index of this row within the board
     */
    public BOARD_COLOR getBoardColor() {
        return boardColor;
    }

    /**
     * Getter method for the space at the index
     * @param index index of the space
     * @return the space at that index
     */
    public Space getSpaceAtIndex(int index){
        return this.spaces.get(index);
    }
}
