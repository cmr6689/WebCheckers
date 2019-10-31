package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private final Gson gson;

    private PlayerLobby playerLobby;

    public PostValidateMoveRoute(PlayerLobby playerLobby){
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        LOG.finer("PostValidateMoveRoute is invoked.");

        request.queryParams("gameID");
        Move move = gson.fromJson(request.queryParams("actionData"), Move.class);
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to validate a move, replace message type of ERROR with INFO

        //validate move using position and board view here


        if(move.isValid()) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your move is valid");
        }else{
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("Your move is not valid");
        }


        // render the View
        return gson.toJson(message);
    }
}
