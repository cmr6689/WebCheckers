package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostSubmitTurnRoute.class.getName());

    private final Gson gson;

    GameData gameData;

    PlayerLobby playerLobby;

    public PostSubmitTurnRoute(GameData gameData, PlayerLobby playerLobby){
        this.gson = new Gson();
        this.gameData = gameData;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostSubmitTurnRoute is invoked.");

        System.err.println("turn was submitted");

        request.queryParams("gameID");
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not submit a turn in the state you are in.");

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        gameData.setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());

        gameData.dataSetup();
        playerLobby.setMap(gameData.getVm());

        // render the View
        return gson.toJson(message);
    }
}
