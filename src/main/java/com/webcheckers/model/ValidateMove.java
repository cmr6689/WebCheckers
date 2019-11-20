package com.webcheckers.model;

import java.util.ArrayList;

public class ValidateMove {

    //boolean to tell if this move was a jump
    boolean jumped = false;

    boolean lastWasJump;

    //since isMovValid changes the actual variable
    //im using a temporary to hold the last one
    boolean tempLast;

    //check if the space is valid
    boolean positionIsValid;

    //check if it is a move and is valid
    boolean moveIsValid;

    //check if it's a jump and is valid
    boolean jumpIsValid;

    //is the move valid
    boolean isValid;

    //boardview
    BoardView board;

    //the piece being moved
    Piece thisPiece;

    //the move
    Move move;

    /**
     * Constructor to check if a move or jump is valid
     */
    public ValidateMove(BoardView board){
        this.board = board;
        this.lastWasJump = board.getLastWasJump();
    }

    public boolean Validator(Piece thisPiece, Move move, Piece.TYPE type, Piece.COLOR color, ArrayList<Position> removed){
        tempLast = board.getLastWasJump();
        int rowsBeingJumped = Math.abs(move.getStart().getRow() - move.getEnd().getRow());
        this.positionIsValid = positionIsValid(board, move.getEnd().getRow(), move.getEnd().getCell());
        this.moveIsValid = moveIsValid(board, move, thisPiece, type, color);
        this.jumpIsValid = jumpIsValid(rowsBeingJumped, move, board, thisPiece, move.getEnd().getRow(), move.getEnd().getCell(),
                type, color);
        this.isValid = positionIsValid && moveIsValid && jumpIsValid;
        if(isValid){
            board.setMoveThisTurn(move);
            if(board.getOriginalPos() == null){
                board.setOriginalPos(move.getStart());
            }
            board.setFinalPos(move.getEnd());
            if(jumped){
                Position pieceRemoved = new Position((move.getStart().getRow()+move.getEnd().getRow())/2,
                        (move.getStart().getCell()+move.getEnd().getCell())/2);
                removed.add(pieceRemoved);
            }
        }
        return isValid;
    }

    /**
     * needs to check the space at that position and look to see if it is a valid space, black and no piece on it
     * @return
     */
    public boolean positionIsValid(BoardView board, int row, int cell){
        Row actualRow = board.getRowAtIndex(row);
        Space actualSpace = actualRow.getSpaceAtIndex(cell);
        //if the space is a valid spot to move to
        if(!actualSpace.isValid()){
            return false;
        }
        return true;
    }

    /**
     * checks whether or not the move is valid
     * @param move the move being made
     * @param thisPiece the piece used in the move
     * @return true if a valid move
     */
    public boolean moveIsValid(BoardView board, Move move, Piece thisPiece, Piece.TYPE type, Piece.COLOR color){
        //Piece.TYPE type = thisPiece.getType();
        lastWasJump = board.getLastWasJump();
        if(board.getNumMovs() > 0 && !(lastWasJump)){
            //if the person is trying to move twice but the last wasn't a jump
            return false;
        }
        if(lastWasJump && (!(Math.abs(move.getStart().getRow()-move.getEnd().getRow()) > 1))){
            //if the last move was a jump but they're not trying to jump again
            return false;
        }
        if(!(Math.abs(move.getStart().getRow()-move.getEnd().getRow()) > 1) && (Math.abs(move.getStart().getCell()-move.getEnd().getCell()) > 1)){
            return false;
        }
        if((move.getStart().getCell() == move.getEnd().getCell()) && (board.getNumMovs() < 2)){
            //if they're trying to move directly across the board without jumping 2 pieces
            return false;
        }
        if(color.equals(Piece.COLOR.RED) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
            //if it's not king it cannot move backwards
            return false;
        }else if (color.equals(Piece.COLOR.WHITE) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) < 0)) {
            //if it's not king it cannot move backwards
            return false;
        }
        board.setLastWasJump(false);
        jumped = false;
        return true;
    }

    /**
     * check if the piece is trying to jump and if the jump is valid
     * true if the piece isn't trying to jump
     * false if there is a piece in the end destination
     * @return
     */
    public boolean jumpIsValid(int rowsBeingJumped, Move move,
                               BoardView board, Piece thisPiece, int row, int cell,
                               Piece.TYPE type, Piece.COLOR color){
        int tempRowInt;
        int tempCellInt;
        lastWasJump = tempLast;
        if(board.getNumMovs() > 1 && !(lastWasJump)){
            //if the person is trying to move twice
            return false;
        }
        if(!(Math.abs(move.getEnd().getRow()-move.getStart().getRow()) > 1)){
            //if there is no jump just return true, the other methods do the checking
            //board.setLastWasJump(false); this was causing the bug.
            jumped = false;
            return true;
        }
        if((!(positionIsValid(board, row, cell)))|| (rowsBeingJumped >= 3)){
            //the spot is invalid or the jump is too far
            //board.setLastWasJump(false);
            jumped = false;
            return false;
        }else{
                //set the row and the cell of the piece being jumped
                tempRowInt = ((move.getStart().getRow() + move.getEnd().getRow()) / 2);
                tempCellInt = ((move.getStart().getCell() + move.getEnd().getCell()) / 2);
                int stuff = (Math.abs(move.getStart().getCell()-move.getEnd().getCell()));
                if(stuff > 3){
                    return false;
                }
                Piece tempPiece = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece();
                //create a position for where the piece will be removed from
                //Position pieceRemoved = new Position(tempRowInt,tempCellInt);
                if(tempPiece == null) {
                    //there is no piece being jumped
                    //board.setLastWasJump(false);
                    jumped = false;
                    return false;
                }
                //get the color of the piece at the location
                Piece.COLOR newColor = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                //type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (newColor != color) {
                    if (type.equals(Piece.TYPE.SINGLE) && color.equals(Piece.COLOR.RED) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
                        //if it's not king and it's red it cannot move backwards
                        //board.setLastWasJump(false);
                        //jumped = false;
                        return false;
                    }else if (type.equals(Piece.TYPE.SINGLE) && color.equals(Piece.COLOR.WHITE) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) < 0)){
                        //if it's not king and it's white it cannot move backwards
                        //board.setLastWasJump(false);
                        //jumped = false;
                        return false;
                    }
                    board.setLastWasJump(true);
                    jumped = true;
                    //removedPieces.add(pieceRemoved);
                    return true;
                } else {
                    //if it's trying to jump a piece of the same color it can't
                    //board.setLastWasJump(false);
                    //jumped = false;
                    return false;
                }
        }
    }

    /**
     *
     * @return
     */
    public Boolean getIsValid(){

        return isValid;
    }

    public void setThisPiece(Piece piece){
        thisPiece = piece;
    }

    public void setMove(Move move){
        this.move = move;
    }

    /**
     *
     */
    public Boolean getJumped(){
        return this.jumped;
    }

}
