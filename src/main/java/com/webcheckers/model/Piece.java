package com.webcheckers.model;

public class Piece {

    /**
     * enum to represent the type the piece is (if it's a normal piece or a king)
     */
    private enum type{
        SINGLE,KING;
    }

    /**
     * represents the color of the piece
     */
    private enum color{
        RED,WHITE;
    }

    //the type of the piece
    private type type;
    //the color of the piece
    private color color;

    /**
     *
     * @param type
     * @param color
     */
    public Piece(type type, color color){
        this.type = type;
        this.color = color;
    }

    /**
     * @return The type of this piece
     */
    public type getType(){
        return type;
    }

    /**
     * @return The color of this piece
     */
    public color getColor(){
        return color;
    }
}
