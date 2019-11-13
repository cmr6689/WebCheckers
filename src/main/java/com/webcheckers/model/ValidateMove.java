package com.webcheckers.model;

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

    /**
     * Constructor to check if a move or jump is valid
     */
    public ValidateMove(BoardView board){
        this.board = board;
    }

    public boolean Validator(Piece thisPiece, Move move){
        int rowsBeingJumped = Math.abs(move.getStart().getRow() - move.getEnd().getRow());
        this.positionIsValid = positionIsValid(board, move.getEnd().getRow(), move.getEnd().getCell());
        this.moveIsValid = moveIsValid(board, move, thisPiece);
        this.jumpIsValid = jumpIsValid(rowsBeingJumped, move, board, thisPiece, move.getEnd().getRow(), move.getEnd().getCell());
        this.isValid = positionIsValid && moveIsValid && jumpIsValid;
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
        if(board.getNumMovs() > 0){
            return false;
        }
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
    public boolean moveIsValid(BoardView board, Move move, Piece thisPiece){
        Piece.TYPE type;
        type = thisPiece.getType();
        if(board.getNumMovs() > 1 && !(lastWasJump)){
            //if the person is trying to move twice
            return false;
        }
        if((move.getStart().getCell() == move.getEnd().getCell()) && (board.getNumMovs() < 2)){
            //if they're trying to move directly across the board without jumping 2 pieces
            return false;
        }
        if(thisPiece.getColor().equals(Piece.COLOR.RED) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
            //if it's not king it cannot move backwards
            return false;
        }else if (thisPiece.getColor().equals(Piece.COLOR.WHITE) && type.equals(Piece.TYPE.SINGLE) &&
                !((move.getStart().getRow() - move.getEnd().getRow()) < 0)) {
            //if it's not king it cannot move backwards
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
                               BoardView board, Piece thisPiece, int row, int cell){
        int tempRowInt;
        int tempCellInt;
        Piece.TYPE type;
        Piece.COLOR color;
        if(board.getNumMovs() > 1 && !(lastWasJump)){
            //if the person is trying to move twice
            return false;
        }
        if(!(positionIsValid(board, row, cell)) || (rowsBeingJumped >= 3)){
            //the spot is invalid or the jump is too far
            lastWasJump = false;
            jumped = false;
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
                if(tempPiece == null) {
                    //there is no piece being jumped
                    lastWasJump = false;
                    jumped = false;
                    return false;
                }
                //get the color of the piece at the location
                color = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (color != thisPiece.getColor()) {
                    //TODO Fix the second condition after boards flip
                    if (type.equals(Piece.TYPE.SINGLE) && thisPiece.getColor().equals(Piece.COLOR.RED) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
                        //if it's not king and it's red it cannot move backwards
                        lastWasJump = false;
                        jumped = false;
                        return false;
                    }else if (type.equals(Piece.TYPE.SINGLE) && thisPiece.getColor().equals(Piece.COLOR.WHITE) &&
                            !((move.getStart().getRow() - move.getEnd().getRow()) < 0)){
                        //if it's not king and it's white it cannot move backwards
                        lastWasJump = false;
                        jumped = false;
                        return false;
                    }
                    lastWasJump = true;
                    jumped = true;
                    return true;
                } else {
                    //if it's trying to jump a piece of the same color it can't
                    lastWasJump = false;
                    jumped = false;
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
}
