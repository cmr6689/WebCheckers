package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.post;

/**
 * This class handles the ajax call of /validateMove and responds with a json message
 *
 * @author Team-E
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostValidateMoveRoute.class.getName());

    private final Gson gson;

    private PlayerLobby playerLobby;

    private Piece.TYPE originalType;

    private Piece.COLOR originalColor;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby the lobby of all the players
     */
    public PostValidateMoveRoute(PlayerLobby playerLobby){
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
        LOG.config("PostValidateMoveRoute is invoked by " + myPlayer.getName() + ".");

        Move move = gson.fromJson(request.queryParams("actionData"), Move.class);

        ResponseMessage message = new ResponseMessage();

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        int thisRow = move.getStart().getRow();
        int thisCell = move.getStart().getCell();
        Piece thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
        if(board.getNumMovs() == 0) {
            originalType = thisPiece.getType();
            originalColor = thisPiece.getColor();
        }

        ValidateMove MoveValidator = new ValidateMove(board);
        boolean isValid = MoveValidator.Validator(thisPiece, move, originalType, originalColor, board.getRemovedPieces());
        boolean jumped = MoveValidator.getJumped();

        MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));
        moveCheck.checkMoves();

        if(isValid && board.getLastWasJump()) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your jump is valid");
            httpSession.attribute("move", move);
            httpSession.attribute("validator", MoveValidator);
            httpSession.attribute("piece", thisPiece);
            board.setRemovedPiece(new Position(thisRow, thisCell));
            board.increaseNumMovs();
        } else if(playerLobby.getGame(myPlayer).getPlayer1().equals(myPlayer) && moveCheck.redCanJump()) {
            message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("You must make available jump moves!");
        } else if (playerLobby.getGame(myPlayer).getPlayer2().equals(myPlayer) && moveCheck.whiteCanJump()) {
            message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("You must make available jump moves!");
        } else if (moveCheck.getJumpChain(move, board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece()).contains(move)){
            ArrayList<Move> moves = moveCheck.getJumpChain(move, board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece());
            if(!moves.get(moves.size() - 1).equals(move)){
                message = new ResponseMessage();
                message.setType(ResponseMessage.MessageType.ERROR);
                message.setText("You must make available jump moves!");
            }
        } else if (isValid && (!moveCheck.whiteCanJump() || !moveCheck.redCanJump())) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your move is valid");
            httpSession.attribute("move", move);
            httpSession.attribute("validator", MoveValidator);
            httpSession.attribute("piece", thisPiece);
            board.increaseNumMovs();
        } else {
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("Your move is not valid");
        }

        // render the View
        return gson.toJson(message);
    }
}
