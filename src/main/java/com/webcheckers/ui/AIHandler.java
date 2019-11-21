package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;

import java.util.ArrayList;

/**
 * This class handles the AI Player's game play
 *
 * @author - Matthew Klein
 */
public class AIHandler {

    private PlayerLobby playerLobby;
    private Player myPlayer;
    //if a move has been found
    private boolean done = false;
    //if a jump has been found
    private boolean jumped = false;
    //list of jumps if multiple jumps available to player
    private ArrayList<Move> jumpChain;

    /**
     * Handler for the AI enhancement
     * @param playerLobby - central storage for player and the game center
     * @param myPlayer - player playing against the AI
     */
    public AIHandler(PlayerLobby playerLobby, Player myPlayer) {
        this.playerLobby = playerLobby;
        this.myPlayer = myPlayer;
    }

    /**
     * Creates a new move for the AI depending on whether or not a Jump is available
     */
    public void AIMove() {
        MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));
        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        BoardHandler boardHandler = new BoardHandler(board);
        playerLobby.getGame(myPlayer).checkGameOver();
        if (playerLobby.getGame(myPlayer).getMap().get("modeOptionsAsJSON") == null) {
            //loop through rows
            for (int i = 0; i < 8; i++) {
                //loop through spaces
                for (int j = 0; j < 8; j++) {
                    //check jumps first if one has not been found already
                    if (!done) {
                        Position position = new Position(i, j);
                        if (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                                (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                        || playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                            //Check if jump is possible
                            if (moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                    position).size() > 0) {
                                Move jump = moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                        position).get(0);
                                jumpChain = moveCheck.getJumpChain(jump, playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece());
                                if (!jumpChain.isEmpty()) {
                                    Position pieceRemoved = new Position((jump.getStart().getRow() + jump.getEnd().getRow()) / 2,
                                            (jump.getStart().getCell() + jump.getEnd().getCell()) / 2);
                                    playerLobby.getGame(myPlayer).getBoardView1().setRemovedPiece(pieceRemoved);
                                    boardHandler.setBoardCond(jump.getStart(), jump.getEnd());
                                    for (Move move : jumpChain) {
                                        pieceRemoved = new Position((move.getStart().getRow() + move.getEnd().getRow()) / 2,
                                                (move.getStart().getCell() + move.getEnd().getCell()) / 2);
                                        playerLobby.getGame(myPlayer).getBoardView1().setRemovedPiece(pieceRemoved);
                                        boardHandler.setBoardCond(move.getStart(), move.getEnd());
                                    }
                                    done = true;
                                    jumped = true;
                                } else {
                                    Position pieceRemoved = new Position((jump.getStart().getRow() + jump.getEnd().getRow()) / 2,
                                            (jump.getStart().getCell() + jump.getEnd().getCell()) / 2);
                                    playerLobby.getGame(myPlayer).getBoardView1().setRemovedPiece(pieceRemoved);
                                    boardHandler.setBoardCond(jump.getStart(), jump.getEnd());
                                    done = true;
                                    jumped = true;
                                }
                            }
                        }
                    }
                }
            }
            //if no jump found then check simple moves
            if (!jumped) {
                for (int i = 0; i < 8; i++) {
                    //loop through spaces
                    for (int j = 0; j < 8; j++) {
                        if (!done) {
                            Position position = new Position(i, j);
                            if (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                                    (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                            || playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                                //Check if move is possible
                                if (moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                        position).size() > 0) {
                                    Move move = moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                            position).get(0);
                                    boardHandler.setBoardCond(move.getStart(), move.getEnd());
                                    done = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter for the done boolean
     * @return whether or not a move has been found or not
     */
    public boolean getDone(){
        return this.done;
    }

    /**
     * Getter for the jumped boolean
     * @return whether or not a jump is available
     */
    public boolean getJumps(){
        return this.jumped;
    }

    /**
     * Setter for the jump chain list
     * @param jumpChain - list of jumps to be performed
     */
    public void setJumpChain(ArrayList<Move> jumpChain){
        this.jumpChain = jumpChain;
    }

    /**
     * Getter for the jump chain list
     * @return the list of jumps
     */
    public ArrayList<Move> getJumpChain(){
        return this.jumpChain;
    }
}
