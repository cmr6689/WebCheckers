package com.webcheckers.model;

public class Space {

    //enum to represent the color of the space
    private enum color{
        BLACK,WHITE;
    }

    //the cell index from 0 to 7
    private int cellIdx;
    //the piece on this space
    private Piece piece;
    //the color of the space
    private static color color;

    public Space(Piece piece, int cellIdx){
        this.piece = piece;
        this.cellIdx = cellIdx;
        ;this.color = color;
    }

    /**
     * @return The index of the cell
     */
    public int getCellIdx(){
        return cellIdx;
    }

    /**
     * This method will return true if the space is a valid location to place a piece;
     * that is, it is a dark square and has no other piece on it.
     * @return true if the space is a valid location and is Black
     */
    public boolean isValid(){
        if(this.piece.equals(null) && color.equals(Space.color.BLACK)){
            return true;
        }
        return false;
    }

    /**
     * @return The piece that resides on this space, if any
     */
    public Piece getPiece() {
        return piece;
    }
}
