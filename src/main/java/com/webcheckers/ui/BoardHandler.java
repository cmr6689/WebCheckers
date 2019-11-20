package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;

import java.util.ArrayList;

public class BoardHandler {

    private BoardView board;
    ArrayList<Position> removedPs = new ArrayList<>();

    public BoardHandler(BoardView board){
        this.board = board;
    }

    public void setBoard(){
        Position start = board.getOriginalPos();
        Position end = board.getFinalPos();
        removedPs = board.getRemovedPieces();
        Piece thisPiece = board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).getPiece();
        board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).removePiece();
        if(((board.getRowAtIndex(end.getRow()).equals(board.getRowAtIndex(0)) && thisPiece.getColor().equals(Piece.COLOR.RED)) ||
                (board.getRowAtIndex(end.getRow()).equals(board.getRowAtIndex(7)) && thisPiece.getColor().equals(Piece.COLOR.WHITE))) &&
                !thisPiece.getType().equals(Piece.TYPE.KING)){
            thisPiece.setType(Piece.TYPE.KING);
            board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(thisPiece);
        }else {
            board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(thisPiece);
        }
        if(removedPs.size() != 0){
            for(Position p: removedPs){
                board.getRowAtIndex(p.getRow()).getSpaceAtIndex(p.getCell()).removePiece();
            }
        }
        board.setLastWasJump(false);
        board.resetPositions();
        board.clearMovesThisTurn();
        board.clearRemovedPieces();
        board.resetMovs();
    }
    public void setBoardCond(Position start, Position end){
        removedPs = board.getRemovedPieces();
        Piece thisPiece = board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).getPiece();
        board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).removePiece();
        if(((board.getRowAtIndex(end.getRow()).equals(board.getRowAtIndex(0)) && thisPiece.getColor().equals(Piece.COLOR.RED)) ||
                (board.getRowAtIndex(end.getRow()).equals(board.getRowAtIndex(7)) && thisPiece.getColor().equals(Piece.COLOR.WHITE))) &&
                !thisPiece.getType().equals(Piece.TYPE.KING)){
            thisPiece.setType(Piece.TYPE.KING);
            board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(thisPiece);
        }else {
            board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(thisPiece);
        }
        if(removedPs.size() != 0){
            for(Position p: removedPs){
                board.getRowAtIndex(p.getRow()).getSpaceAtIndex(p.getCell()).removePiece();
            }
        }
        board.setLastWasJump(false);
        board.resetPositions();
        board.clearMovesThisTurn();
        board.clearRemovedPieces();
        board.resetMovs();
    }

}
