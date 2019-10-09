package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

public class GetGameRoute implements Route{

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.GetGameRoute.class.getName());

    private static final Message GAME_MSG = Message.info(String.format("You are playing a game of Webcheckers with %s", "player"));

    private final TemplateEngine templateEngine;

    private PlayerLobby lobby = new PlayerLobby();


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetGameRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Webcheckers");

        // display a user message in the Game page
        vm.put("message", GAME_MSG);

        //variables for game
        vm.put("currentUser", new Player("name").getName());
        vm.put("viewMode", "PLAY");
        vm.put("modeOptionsAsJSON!", null);
        vm.put("redPlayer", new Player("Red").getName());
        vm.put("whitePlayer", new Player("White").getName());
        vm.put("activeColor", "RED");
        vm.put("board", new BoardView());

        // render the View
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}

