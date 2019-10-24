package com.webcheckers.model;

public class Move {

    //attributes for a move
    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return this.start;
    }

    public Position getEnd(){
        return this.end;
    }

    /**
     * if the designated spot isn't valid then the move isn't valid,
     * otherwise it is
     * @return
     */
    public boolean isValid(){
        return end.isValid();
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Move)) return false;
        final Move that = (Move) o;
        return ((this.start.equals(that.start)) && (this.end.equals(that.end)));
    }
}
