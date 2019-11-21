package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import spark.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /helpMove and responds with a json message
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

        MoveHelper helper = new MoveHelper(playerLobby.getGame(myPlayer));
        if (myPlayer.equals(playerLobby.getGame(myPlayer).getPlayer1())) {
            ResponseMessage message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.INFO);
            if (!helper.getRedJump().equals("nope")) message.setText(helper.getRedJump());
            else message.setText(helper.getRedMove());
            return gson.toJson(message);
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.INFO);
            if (!helper.getWhiteJump().equals("nope")) message.setText(helper.getWhiteJump());
            else message.setText(helper.getWhiteMove());
            return gson.toJson(message);
        }
    }
}
