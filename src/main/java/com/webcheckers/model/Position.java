package com.webcheckers.model;

public class Position {

    //attributes need for a position
    //the BoardView
    private BoardView board;
    //the int representation of the row
    private int row;
    //the int representation of the cell(space)
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow(){
        return this.row;
    }

    public int getCell(){
        return this.cell;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position that = (Position) o;
        return ((this.cell == that.cell) && (this.row == that.row));
    }
}
