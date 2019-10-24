package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import com.webcheckers.model.Piece.COLOR;

public class Space {

    //enum to represent the color of the space
    public enum BOARD_COLOR{
        BLACK,WHITE;
    }

    //the cell index from 0 to 7
    private int cellIdx;
    //the piece on this space
    private Piece piece;
    //the color of the space
    private BOARD_COLOR color;

    public Space(Piece piece, int cellIdx, BOARD_COLOR color){
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.color = color;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
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
        return this.getPiece() == null && color.equals(BOARD_COLOR.BLACK);
    }

    /**
     * @return The piece that resides on this space, if any
     */
    public Piece getPiece() {
        return this.piece;
    }
}
