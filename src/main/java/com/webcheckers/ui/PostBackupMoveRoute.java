package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The UI controller for the /backupMove ajax call during a game
 */
public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostBackupMoveRoute.class.getName());

    private final Gson gson;

    //new
    PlayerLobby playerLobby;

    /**
     * Constructor to set the gson
     */
    public PostBackupMoveRoute(PlayerLobby playerLobby){
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        LOG.config("PostBackupMoveRoute is invoked by " + myPlayer.getName() + ".");

        //added this to try something
        Piece thisPiece = httpSession.attribute("piece");
        //ValidateMove MoveValidator = httpSession.attribute("validator");
        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        //TODO
        //need to make an arrayList of moves in board and decrease the size and get the end position from the new last
        /**
        if(board.getMovesThisTurn().size() >= 1){
            board.decreaseNumMoves();
            Position start = board.getOriginalPos();
            Position end = board.getFinalPos();
            board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).removePiece();
            board.decrementMovesThisTurn();
            if(board.getMovesThisTurn().size() == 0){
                board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).setPiece(thisPiece);
                board.setLastWasJump(false);
            }else {
                board.getRowAtIndex(board.getMovesThisTurn().get(
                        board.getMovesThisTurn().size() - 1).getEnd().getRow()).getSpaceAtIndex(
                        board.getMovesThisTurn().get(board.getMovesThisTurn().size() - 1).getEnd().getCell()).setPiece(thisPiece);
                board.setFinalPos(board.getMovesThisTurn().get(board.getMovesThisTurn().size() - 1).getEnd());
            }
            if(board.getRemovedPieces().size() != 0){
                board.backupPiece();
            }
        }else{
            //if the number of moves is 0 put back in original spot
            Position start = board.getOriginalPos();
            board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).setPiece(thisPiece);
        }*/

        if(board.getMovesThisTurn().size() >= 1) {
            board.resetMovs();
            board.resetPositions();
            Position start = board.getOriginalPos();
            Position end = board.getFinalPos();
            //board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).removePiece();
            board.clearMovesThisTurn();
            //board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).setPiece(thisPiece);
            board.setLastWasJump(false);
            board.clearRemovedPieces();
        }

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Your move has been backed up");

        // render the View
        return gson.toJson(message);
    }
}
