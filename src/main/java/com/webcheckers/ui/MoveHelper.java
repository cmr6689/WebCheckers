package com.webcheckers.ui;

import com.webcheckers.model.*;

/**
 * Class responsible for the logic behind the Help button in a game
 *
 * @author Team-E
 */
public class MoveHelper {

    //game being played
    private Game game;

    /**
     * Constructor for MoveHelper
     * @param game being played
     */
    public MoveHelper(Game game) {
        this.game = game;
    }

    /**
     * Gets the first available jump for the red player
     * @return string instructions for jump
     *         nope returned if no jumps available
     */
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
                        String temp = jump.getStart().helpString() + " -> " + jump.getEnd().helpString();

                        Piece piece = game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece();
                        for(Move move  : moveChecks.getJumpChain(jump, piece)){
                            temp += " -> " + move.getEnd().helpString();
                        }
                        return temp;
                    }
                }
            }
        }
        return "nope";
    }

    /**
     * Gets the first available move for the red player
     * @return string instructions for move
     */
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

    /**
     * Gets the first available jump for the white player
     * @return string instructions for jump
     *         nope returned if no jumps available
     */
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
                        String temp = jump.getStart().helpString2() + " -> " + jump.getEnd().helpString2();

                        Piece piece = game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece();
                        for(Move move  : moveChecks.getJumpChain(jump, piece)){
                            temp += " -> " + move.getEnd().helpString2();
                        }
                        return temp;
                    }
                }
            }
        }
        return "nope";
    }

    /**
     * Gets the first available move for the white player
     * @return string instructions for move
     */
    public String getWhiteMove() {
        MoveChecks moveChecks = new MoveChecks(game);
        for (int i = 0; i < 8; i++) {
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
