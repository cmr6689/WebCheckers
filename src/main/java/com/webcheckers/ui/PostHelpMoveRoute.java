package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /resign and responds with a json message
 *
 * @author Team-E
 */
public class PostHelpMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param playerLobby the lobby of all the players
     */
    public PostHelpMoveRoute(PlayerLobby playerLobby){
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
        LOG.config("PostHelpTurn is invoked by " + myPlayer.getName() + ".");

        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Help was called.");

        // render the View
        return gson.toJson(message);
    }
}
