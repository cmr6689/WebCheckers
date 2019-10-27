package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.post;
import static spark.route.HttpMethod.post;

public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private static final Message RESIGN_MSG = Message.info("You have resigned from the game");
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    public PostResignRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {

        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostResignRoute is invoked.");

        request.queryParams("gameID");
        vm.put("message", RESIGN_MSG);
        response.body("You have resigned from the game");

       if(playerLobby.getGame().isActive()){
           playerLobby.getGame().setIsActive(false);
       }

        Spark.post("/", (res, rep) -> {
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        });

    // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
