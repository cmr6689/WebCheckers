package com.webcheckers.model;
import com.webcheckers.model.Space.BOARD_COLOR;

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


    public Piece(COLOR color, TYPE type){
        this.type= type;
        this.color= color;
    }

    /**
     * @return The type of this piece
     */
    public TYPE getType(){
        return type;
    }

    /**
     * @return The color of this piece
     */
    public COLOR getColor(){
        return color;
    }

    public void setType(TYPE type){
        this.type = type;
    }

    public void setColor(COLOR color){
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;
        final Piece that = (Piece) obj;
        return this.equals(that);
    }
}
