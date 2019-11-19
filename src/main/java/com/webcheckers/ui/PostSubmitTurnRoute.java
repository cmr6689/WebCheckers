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
        //TODO check if jump is available
        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        LOG.config("PostSubmitTurnRoute is invoked by " + myPlayer.getName() + ".");

        //for the player that needs to refresh when the game has ended
        if (playerLobby.getGameCenter().justEnded(myPlayer)) {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("");
            return gson.toJson(message2);
        }

        ValidateMove MoveValidator = httpSession.attribute("validator");
        MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));

        System.err.println("Im going to check what moves and jumps are available (postSubmitTurnRoute:68)");

//        moveCheck.checkMoves();

        ResponseMessage message;
        //Check if valid move first
        if(moveCheck.jumpAvailable()) {
            message = new ResponseMessage();
            // to successfully resign, replace message type of ERROR with INFO
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("You can not submit a turn in the state you are in.");
        }else{
            message = new ResponseMessage();
            // to successfully resign, replace message type of ERROR with INFO
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("You can submit a turn in the state you are in.");
        }

        //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());

        if (playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor").equals("WHITE")) {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView1());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        } else {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView2());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "WHITE");
        }

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        BoardHandler boardHandler = new BoardHandler(board);
        boardHandler.setBoard();
        return gson.toJson(message);
    }
}
