package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private static final Message RESIGN_MSG = Message.info("You have resigned from the game");

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    public PostResignRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostResignRoute is invoked.");

        request.queryParams("gameID");
        vm.put("message", RESIGN_MSG);
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not resign in the state you are in.");

       if(playerLobby.getGame().isActive()){
           playerLobby.getGame().setIsActive(false);
       }
        // render the View
        return gson.toJson(message);
    }
}
