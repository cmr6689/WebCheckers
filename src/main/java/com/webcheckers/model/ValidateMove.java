package com.webcheckers.model;

import java.util.ArrayList;

public class ValidateMove {

    //boolean to tell if the last move was a jump
    private boolean lastWasJump = false;

    //boolean to tell if this move was a jump
    boolean jumped = false;

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

    ArrayList<Position> removedPieces = new ArrayList<>();

    ArrayList<Move> movesThisTurn = new ArrayList<>();

    /**
     * Constructor to check if a move or jump is valid
     */
    public ValidateMove(BoardView board){
        this.board = board;
    }

    public boolean Validator(Piece thisPiece, Move move, Piece.TYPE type, Piece.COLOR color){
        int rowsBeingJumped = Math.abs(move.getStart().getRow() - move.getEnd().getRow());
        this.positionIsValid = positionIsValid(board, move.getEnd().getRow(), move.getEnd().getCell());
        this.moveIsValid = moveIsValid(board, move, thisPiece, type, color);
        this.jumpIsValid = jumpIsValid(rowsBeingJumped, move, board, thisPiece, move.getEnd().getRow(), move.getEnd().getCell(),
                type, color);
        this.isValid = positionIsValid && moveIsValid && jumpIsValid;
        if(isValid){
            movesThisTurn.add(move);
        }
        return isValid;
    }

    /**
     * needs to check the space at that position and look to see if it is a valid space, black and no piece on it
     * @return
     */
    public boolean positionIsValid(BoardView board, int row, int cell){
        System.err.println(board + " " + row);
        Row actualRow = board.getRowAtIndex(row);
        Space actualSpace = actualRow.getSpaceAtIndex(cell);
        //check to see if the piece has already been moved once
        /*
        if(board.getNumMovs() > 0){
            System.out.println("1");
            return false;
        }
         */
        //if the space is a valid spot to move to
        if(!actualSpace.isValid()){
            System.out.println("2");
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
        if(board.getNumMovs() > 1 && !(lastWasJump)){
            //if the person is trying to move twice
            System.out.println("3");
            return false;
        }
        if((move.getStart().getCell() == move.getEnd().getCell()) && (board.getNumMovs() < 2)){
            //if they're trying to move directly across the board without jumping 2 pieces
            System.out.println("4");
            return false;
        }
        if(color.equals(Piece.COLOR.RED) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
            //if it's not king it cannot move backwards
            System.out.println("5");
            return false;
        }else if (color.equals(Piece.COLOR.WHITE) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) < 0)) {
            //if it's not king it cannot move backwards
            System.out.println("6");
            return false;
        }
        lastWasJump = false;
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
        if(board.getNumMovs() > 1 && !(lastWasJump)){
            //if the person is trying to move twice
            System.out.println("7");
            return false;
        }
        if(!(positionIsValid(board, row, cell)) || (rowsBeingJumped >= 3)){
            //the spot is invalid or the jump is too far
            lastWasJump = false;
            jumped = false;
            System.out.println("8");
            return false;
        }else{
            if(!(Math.abs(move.getStart().getRow()-move.getEnd().getRow()) > 1)){
                //if there is no jump just return true, the other methods do the checking
                lastWasJump = false;
                jumped = false;
                return true;
            }else {
                //set the row and the cell of the piece being jumped
                tempRowInt = ((move.getStart().getRow() + move.getEnd().getRow()) / 2);
                tempCellInt = ((move.getStart().getCell() + move.getEnd().getCell()) / 2);
                Piece tempPiece = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece();
                //create a position for where the piece will be removed from
                Position pieceRemoved = new Position(tempRowInt,tempCellInt);
                if(tempPiece == null) {
                    //there is no piece being jumped
                    lastWasJump = false;
                    jumped = false;
                    System.out.println("9");
                    return false;
                }
                //get the color of the piece at the location
                Piece.COLOR newColor = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                //type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (newColor != color) {
                    //TODO Fix the second condition after boards flip
                    if (type.equals(Piece.TYPE.SINGLE) && color.equals(Piece.COLOR.RED) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
                        //if it's not king and it's red it cannot move backwards
                        lastWasJump = false;
                        jumped = false;
                        System.out.println("10");
                        return false;
                    }else if (type.equals(Piece.TYPE.SINGLE) && color.equals(Piece.COLOR.WHITE) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) < 0)){
                        //if it's not king and it's white it cannot move backwards
                        lastWasJump = false;
                        jumped = false;
                        System.out.println("11");
                        return false;
                    }
                    lastWasJump = true;
                    jumped = true;
                    removedPieces.add(pieceRemoved);
                    return true;
                } else {
                    //if it's trying to jump a piece of the same color it can't
                    lastWasJump = false;
                    jumped = false;
                    System.out.println("12");
                    return false;
                }
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

    /**
     *
     */
    public ArrayList<Position> getRemovedPieces(){
        return this.removedPieces;
    }

    public void clearRemovedPieces(){
        removedPieces.clear();
    }

    public ArrayList<Move> getMovesThisTurn(){
        return movesThisTurn;
    }

    public void clearMovesThisTurn(){
        movesThisTurn.clear();
    }
}
