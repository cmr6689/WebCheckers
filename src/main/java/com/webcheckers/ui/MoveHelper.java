package com.webcheckers.ui;

import com.webcheckers.model.*;

public class MoveHelper {

    private Game game;

    public MoveHelper(Game game) {
        this.game = game;
    }

    public String getRedJump() {
        MoveChecks moveChecks = new MoveChecks(game);
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                //if piece is not null and red
                if (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE))
                                || game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.KING)))) {
                    //if jump is available
                    if (moveChecks.checkSinglePieceJumps(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        //get first jump in returned list
                        Move jump = moveChecks.checkSinglePieceJumps(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        //return first
                        String temp = jump.getStart().helpString() + " -> ";
                        if(!moveChecks.getDoubleJumps().isEmpty()){
                            Move tempM = moveChecks.getDoubleJumps().get(jump).get(0);
                            temp += tempM.getStart().helpString() + " -> " + tempM.getEnd().helpString();
                            return temp;
                        }

                        return jump.getStart().helpString() + " -> " + jump.getEnd().helpString();

                        //if no jump is available check simple moves
                    }
                }
            }
        }
        return "nope";
    }
    public String getRedMove() {
        MoveChecks moveChecks = new MoveChecks(game);
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                //if piece is not null and red
                if (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE))
                                || game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.KING)))) {
                    //if jump is available
                    if (moveChecks.checkSinglePieceMoves(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        //get first move in returned list
                        Move move = moveChecks.checkSinglePieceMoves(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        //return first move
                        return move.getStart().helpString() + " -> " + move.getEnd().helpString();
                    }
                }
            }
        }
        //no jumps or moves available
        return "No moves available!";
    }

    public String getWhiteJump() {
        MoveChecks moveChecks = new MoveChecks(game);
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                if (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                || game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                    if (moveChecks.checkSinglePieceJumps(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move jump = moveChecks.checkSinglePieceJumps(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        String temp = jump.getStart().helpString() + " -> ";
                        if(!moveChecks.getDoubleJumps().isEmpty()){
                            Move tempM = moveChecks.getDoubleJumps().get(jump).get(0);
                            temp += tempM.getStart().helpString() + " -> " + tempM.getEnd().helpString();
                            return temp;
                        }
                        return jump.getStart().helpString2() + " -> " + jump.getEnd().helpString2();
                    }
                }
            }
        }
        return "nope";
    }

    public String getWhiteMove() {
        MoveChecks moveChecks = new MoveChecks(game);
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                if (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                || game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                    if (moveChecks.checkSinglePieceMoves(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move move = moveChecks.checkSinglePieceMoves(game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        return move.getStart().helpString2() + " -> " + move.getEnd().helpString2();
                    }
                }
            }
        }
        return "No moves available!";
    }
}
