package com.webcheckers.model;

/**
 * The Position class handles the positions for the move class,
 * it takes in a board view and iterates through the rows and cell
 *
 * @author Team-E
 */
public class Position {

    //attributes need for a position
    //the BoardView
    private BoardView board;
    //the int representation of the row
    private int row;
    //the int representation of the cell(space)
    private int cell;

    /**
     * Constructor for the position class that sets the row and cell numbers
     * @param row the row
     * @param cell the cell in the row
     */
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    /**
     * Get the row number
     * @return the row number
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Get the cell number
     * @return the cell number
     */
    public int getCell(){
        return this.cell;
    }

    /**
     * Method to check if two positions equal each other or not
     * @param o a position
     * @return true or false
     */
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position that = (Position) o;
        return ((this.cell == that.cell) && (this.row == that.row));
    }
}
