package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;

public class AIHandler {

    private PlayerLobby playerLobby;
    private Player myPlayer;

    public AIHandler(PlayerLobby playerLobby, Player myPlayer){
        this.playerLobby = playerLobby;
        this.myPlayer = myPlayer;
    }

    public void AIMove(){
        MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                if (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                || playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                    if (moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move jump = moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(jump.getEnd().getRow()).getSpaceAtIndex(jump.getEnd().getCell()).removePiece();
                        playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(jump.getEnd().getRow()).getSpaceAtIndex(jump.getEnd().getCell()).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
                        break;
                    } else if (moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move move = moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(move.getStart().getRow()).getSpaceAtIndex(move.getStart().getCell()).removePiece();
                        playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(move.getEnd().getRow()).getSpaceAtIndex(move.getEnd().getCell()).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
                        break;
                    }
                }
            }
        }
    }
}
