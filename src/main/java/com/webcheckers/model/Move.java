package com.webcheckers.model;

public class Move {

    //attributes for a move
    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
        //if the player is attempting to jump a piece(moving across 2 rows) then set the boolean to be true
    }

    public Position getStart(){
        return this.start;
    }

    public Position getEnd(){
        return this.end;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Move)) return false;
        final Move that = (Move) o;
        return ((this.start.equals(that.start)) && (this.end.equals(that.end)));
    }
}
