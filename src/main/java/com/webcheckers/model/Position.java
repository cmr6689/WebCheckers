package com.webcheckers.model;

public class Position {

    //attributes need for a position
    //the BoardView
    private BoardView board;
    //the int representation of the row
    private int row;
    //the int representation of the cell(space)
    private int cell;
    //the actual row
    private Row actualRow;
    //the actual space
    private Space actualSpace;

    public Position(BoardView boardView, int row, int cell){
        this.board = boardView;
        this.row = row;
        this.cell = cell;
    }

    public int getRow(){
        return this.row;
    }

    public int getCell(){
        return this.cell;
    }

    /**
     * needs to check the space at that position and look to see if it is a valid space, black and no piece on it
     * @return
     */
    public boolean isValid(){
        System.err.println(board + " " + row);
        actualRow = board.getRowAtIndex(row);
        actualSpace = actualRow.getSpaceAtIndex(cell);
        if(actualSpace.isValid()){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position that = (Position) o;
        return ((this.cell == that.cell) && (this.row == that.row));
    }
}
