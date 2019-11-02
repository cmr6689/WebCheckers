package com.webcheckers.ui;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostBackupMoveRoute.class.getName());

    private final Gson gson;

    public PostBackupMoveRoute(){
        this.gson = new Gson();
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostBackupMoveRoute is invoked.");

        request.queryParams("gameID");
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Your move has been backed up");

        // render the View
        return gson.toJson(message);
    }
}
