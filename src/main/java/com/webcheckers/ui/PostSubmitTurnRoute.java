package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private static final Message SUBMIT_MSG = Message.info("The turn has been submitted.");

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    public PostSubmitTurnRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostSubmitTurnRoute is invoked.");

        request.queryParams("gameID");
        vm.put("message", SUBMIT_MSG);
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not submit a turn in the state you are in.");

        if(playerLobby.getGame().isActive()){
            playerLobby.getGame().setIsActive(false);
        }
        // render the View
        return gson.toJson(message);
    }
}
