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
 * Route that handles the ajax call of checking whose turn it is while it is currently not your turn.
 *
 * @author Team-E
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     */
    public PostCheckTurnRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        this.gson = new Gson();
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
        LOG.config("PostCheckTurn is invoked by " + myPlayer.getName() + ".");

        if(playerLobby.getGameCenter().getGame(myPlayer) != null && playerLobby.getGameCenter().getGame(myPlayer).getPlayer2().getAI()){
            new AIHandler(playerLobby, myPlayer).AIMove();
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        }
        //for the player that needs to refresh when the game has ended
        if (playerLobby.getGameCenter().justEnded(myPlayer)) {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("true");
            return gson.toJson(message2);

            //game is no longer active
        }else if (playerLobby.getGameCenter().getGame(myPlayer) == null || !playerLobby.getGameCenter().getGame(myPlayer).isActive()) {
            ResponseMessage message1 = new ResponseMessage();
            message1.setType(ResponseMessage.MessageType.INFO);
            message1.setText("false");
            return gson.toJson(message1);
        } else {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("true");
            return gson.toJson(message2);
        }
    }
}