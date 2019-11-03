package com.webcheckers.model;
import com.webcheckers.model.Space.BOARD_COLOR;

/**
 * The Piece class handles each piece on the board's color, and type
 *
 * @author Team-E
 */
public class Piece {
    /**
     * enum to represent the type the piece is (if it's a normal piece or a king)
     */
    public enum TYPE{
        SINGLE,KING;
    }

    /**
     * represents the color of the piece
     */
    public enum COLOR{
        RED,WHITE;
    }

    //the type of the piece
    private TYPE type;
    //the color of the piece
    private COLOR color;

    /**
     * Constructor for the piece class
     * @param color the color of the piece
     * @param type the type of the piece either a king or a single
     */
    public Piece(COLOR color, TYPE type){
        this.type= type;
        this.color= color;
    }

    /**
     * Getter for the type of the piece
     * @return The type of this piece
     */
    public TYPE getType(){
        return type;
    }

    /**
     * Getter for the color of the piece
     * @return The color of this piece
     */
    public COLOR getColor(){
        return color;
    }

    /**
     * Setter for the type of the piece
     * @param type single or king
     */
    public void setType(TYPE type){
        this.type = type;
    }

    /**
     * Setter for the color of the piece
     * @param color red or white
     */
    public void setColor(COLOR color){
        this.color = color;
    }

    /**
     * Set the type of the piece to be a king
     */
    public void kingPiece(){
        this.type = TYPE.KING;
    }

    /**
     * Method to check if two pieces are equal to each other
     * @param obj a piece
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;
        final Piece that = (Piece) obj;
        return ((this.type.equals(that.type)) && (this.color.equals(that.color)));
    }

    @Override
    public String toString(){
        return "This piece is a " + getType() + " and has color " + getColor();
    }
}
