package com.webcheckers.model;

/**
 * The Position class handles the positions for the move class,
 * it takes in a board view and iterates through the rows and cell
 *
 * @author Team-E
 */
public class Position {
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

    /**
     * String used for the help enhancement feature
     * @return this cell and row
     */
    public String helpString() {
        return "(" + cell + "," + (7-row) + ")";
    }

    /**
     * String used for the help enhancement feature
     * @return this cell and row
     */
    public String helpString2() {
        return "(" + (7-cell) + "," + row + ")";
    }

    /**
     * prints this positions cell and row
     * @return the positions row and cell
     */
    @Override
    public String toString(){
        return "The position of this piece is row " + row + " and cell " + cell;
    }
}
