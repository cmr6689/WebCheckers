package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /validateMove and responds with a json message
 *
 * @author Team-E
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostValidateMoveRoute.class.getName());

    private final Gson gson;

    private PlayerLobby playerLobby;

    boolean jumped;

    boolean isValid;

    Piece.TYPE originalType;

    Piece.COLOR originalColor;

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
        System.out.println("Called Validate");
        Map<String, Object> vm = new HashMap<>();

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        LOG.config("PostValidateMoveRoute is invoked.");

        System.out.println(request.queryParams("actionData"));

        request.queryParams("gameID");
        Move move = gson.fromJson(request.queryParams("actionData"), Move.class);
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to validate a move, replace message type of ERROR with INFO
        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        int thisRow = move.getStart().getRow();
        int thisCell = move.getStart().getCell();
        Piece thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
        if(board.getNumMovs() == 0){
            originalType = thisPiece.getType();
            originalColor = thisPiece.getColor();
        }

        ValidateMove MoveValidator = new ValidateMove(board);
        isValid = MoveValidator.Validator(thisPiece,move, originalType,originalColor,board.getRemovedPieces());
        jumped = MoveValidator.getJumped();

        if(isValid) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your move is valid");
            httpSession.attribute("move", move);
            httpSession.attribute("validator", MoveValidator);
            //increase the number of movs this turn
            board.increaseNumMovs();
        }else{
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("Your move is not valid");
        }


        // render the View
        return gson.toJson(message);
    }
}
