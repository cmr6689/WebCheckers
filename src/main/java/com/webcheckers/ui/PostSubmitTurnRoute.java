package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /submitTurn and responds with a json message
 *
 * @author Team-E
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostSubmitTurnRoute.class.getName());

    private final Gson gson;

    PlayerLobby playerLobby;

    ArrayList<Position> removedPs = new ArrayList<>();


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby the lobby of all the players
     */
    public PostSubmitTurnRoute(PlayerLobby playerLobby) {
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    /**
     * Respond to the ajax call with a gson to json message
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        LOG.config("PostSubmitTurnRoute is invoked by " + myPlayer.getName() + ".");

        Move move = httpSession.attribute("move");
        Piece piece = httpSession.attribute("piece");
        MoveChecks moveCheck = new MoveChecks(playerLobby.getGame(myPlayer));

        /*ArrayList<Move> moves = moveCheck.getJumpChain(move, piece);
        if(!moves.isEmpty()) {
            if (!moves.get(moves.size() - 1).equals(move)) {
                ResponseMessage message = new ResponseMessage();
                message.setType(ResponseMessage.MessageType.ERROR);
                message.setText("You must make all possible jumps");

                BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
                BoardHandler boardHandler = new BoardHandler(board);
                boardHandler.setBoard();
                return gson.toJson(message);
            }
        }*/

        //for the player that needs to refresh when the game has ended
        if (playerLobby.getGameCenter().justEnded(myPlayer)) {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("");
            return gson.toJson(message2);
        }

        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can submit a turn in the state you are in.");

        if(playerLobby.getGameCenter().getGame(myPlayer) == null){
            return gson.toJson(message);
        }

        if (playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor").equals("WHITE")) {
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        } else {
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "WHITE");
        }

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        BoardHandler boardHandler = new BoardHandler(board);
        boardHandler.setBoard();

        return gson.toJson(message);
    }
}
