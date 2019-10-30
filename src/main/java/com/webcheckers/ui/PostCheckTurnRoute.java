package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Route that handles the ajax call of checking whose turn it is while it is currently not your turn.
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    private GameData gameData;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param gameData the vm map data of the game
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameData gameData) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
        this.gameData = gameData;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurn is invoked.");

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        ResponseMessage message = new ResponseMessage();

        if (gameData.getVm().get("currentUser") == myPlayer) {
            /*if (gameData.getVm().get("redPlayer") == myPlayer) {
                gameData.setActiveColor("RED");
            } else {
                gameData.setActiveColor("WHITE");
            }*/
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("It is your turn.");
            // render the View
            return gson.toJson(message);
        } else {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("It is not your turn.");
            // render the View
            return gson.toJson(message);
        }
    }
}