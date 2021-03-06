package com.webcheckers.model;

/**
 * A row will be made of 8 objects of this class
 * Class used to represent a space on the board(a spot that can hold a piece)
 *
 * @author Team-E
 */
public class Space {
    //enum to represent the color of the space
    public enum BOARD_COLOR{
        BLACK,WHITE;
    }

    //the cell index from 0 to 7
    private int cellIdx;
    //the position this space is at
    private Position position;
    //the piece on this space
    private Piece piece;
    //the color of the space
    private BOARD_COLOR color;

    /**
     * Creates a new space that will be a part of a row and will potentially have a piece on it
     * @param piece: the piece on this space, null if none
     * @param cellIdx: the index of this space
     * @param color: the color of this space
     */
    public Space(Piece piece, int cellIdx, BOARD_COLOR color){
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.color = color;
    }

    /**
     * Second Constructor for Space
     * @param piece the piece on the space
     * @param cellIDx the index of the space
     * @param color the color of the space
     * @param position the position of the space
     */
    public Space(Piece piece, int cellIDx, BOARD_COLOR color, Position position){
        this(piece, cellIDx, color);
        this.position = position;
    }

    /**
     * getter method
     * @return returns this space's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * sets this space's piece
     * @param piece: piece to put on this space
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * gets this space's index
     * @return The index of the cell
     */
    public int getCellIdx(){
        return cellIdx;
    }

    /**
     * gets this board color of the space
     * @return The board color of the space
     */
    public BOARD_COLOR getBoardColor(){
        return color;
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
     * Gets the current piece on this space
     * @return The piece that resides on this space, if any
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * remove the piece from this space
     */
    public void removePiece(){
        this.piece = null;
    }

    /**
     * prints out the piece, it's cellIdx, and it's color
     * @return the piece on the space, the space's index, and the color of the space
     */
    @Override
    public String toString(){
        return "The piece on this space is " + piece + ", the index of this space is " + cellIdx +
                " and, the color of this space is " + color;
    }
}
