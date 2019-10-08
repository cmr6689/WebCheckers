package com.webcheckers.model;

public class Piece {

    private enum type{
        SINGLE,KING;
    }

    private enum color{
        RED,WHITE;
    }

    private type type;
    private color color;

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
