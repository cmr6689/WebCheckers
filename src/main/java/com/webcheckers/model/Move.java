package com.webcheckers.model;

/**
 * The move class handles any move that a player might make by
 * taking in the positions on the board of that move.
 *
 * @author Team-E
 */
public class Move {

    //attributes for a move
    private Position start;
    private Position end;

    /**
     * Constructor for the move class that sets the positions of the move
     * @param start position on the board where the piece started
     * @param end position on the board where the piece is moved to
     */
    public Move(Position start, Position end){
        //TODO add double jump
        this.start = start;
        this.end = end;
        //if the player is attempting to jump a piece(moving across 2 rows) then set the boolean to be true
    }

    /**
     * Get the starting position of the piece
     * @return the starting position on the board
     */
    public Position getStart(){
        return this.start;
    }

    /**
     * Get the position that the piece is being moved to
     * @return the position on the board that the piece is being moved to
     */
    public Position getEnd(){
        return this.end;
    }

    /**
     * Check to see if two moves equal each other
     * @param o a move
     * @return true or false
     */
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Move)) return false;
        final Move that = (Move) o;
        return ((this.start.equals(that.start)) && (this.end.equals(that.end)));
    }

    /**
     * Method that creates a string of the move being made
     * @return string
     */
    @Override
    public String toString() {
        return "Starting at position " + getStart().getRow() + ":" + getStart().getCell() +
                " and trying to move to position " + getEnd().getRow() + ":" + getEnd().getCell();
    }
}
