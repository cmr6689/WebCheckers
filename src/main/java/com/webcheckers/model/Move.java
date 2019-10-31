package com.webcheckers.model;

public class Move {

    //attributes for a move
    private Position start;
    private Position end;
    private int thisRow;
    private int thisCell;
    private Piece thisPiece;
    private boolean jumpingPiece;
    private int rowsBeingJumped;
    private BoardView board;

    public Move(Position start, Position end){
        this.start = start;
        this.thisCell = start.getCell();
        this.thisRow = start.getRow();
        this.thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
        this.end = end;
        this.board = board;
        //if the player is attempting to jump a piece(moving across 2 rows) then set the boolean to be true
        this.rowsBeingJumped = Math.abs(start.getRow() - end.getRow());
        jumpingPiece = (rowsBeingJumped != 1);
    }

    public Position getStart(){
        return this.start;
    }

    public Position getEnd(){
        return this.end;
    }

    /**
     * if the designated spot isn't valid then the move isn't valid,
     * and if there is now jump available
     * otherwise it is
     * @return
     */
    public boolean isValid(){
        return end.isValid() && jumpIsValid() && moveIsValid();
    }

    public boolean moveIsValid(){
        Piece.TYPE type;
        type = thisPiece.getType();
        if(Math.abs(start.getRow()-end.getRow()) > 1){
            return false;
        }
        if(type == Piece.TYPE.SINGLE && !((start.getRow() - end.getRow()) > 0)) {
            //if it's not king it cannot move backwards
            return false;
        }
        return true;
    }

    /**
     * check if the piece is trying to jump and if the jump is valid
     * true if the piece isn't trying to jump
     * false if there is a piece in the end destination
     * @return
     */
    public boolean jumpIsValid(){
        int tempRowInt;
        int tempCellInt;
        Piece.TYPE type;
        Piece.COLOR color;
        if(jumpingPiece == true){
            if(!(end.isValid()) || (rowsBeingJumped >= 2)){
                return false;
            }else{
                //set the row and the cell of the piece being jumped
                tempRowInt = ((start.getRow() + end.getRow()) / 2);
                tempCellInt = ((start.getCell() + end.getCell()) / 2);
                //get the color of the piece at the location
                color = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (color != thisPiece.getColor()) {
                    if(type == Piece.TYPE.SINGLE && !((start.getRow() - end.getRow()) > 0)) {
                        //if it's not king it cannot move backwards
                        return false;
                    }
                    return true;
                }else{
                    return false;
                }
            }

        }else{
            return true;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Move)) return false;
        final Move that = (Move) o;
        return ((this.start.equals(that.start)) && (this.end.equals(that.end)));
    }
}
