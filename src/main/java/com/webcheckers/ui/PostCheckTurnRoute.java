package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    private GameData gameData;

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
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("It is your turn.");
            // render the View
            return gson.toJson(message);
        } else {
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("It is not your turn.");
            // render the View
            return gson.toJson(message);
        }
    }
}