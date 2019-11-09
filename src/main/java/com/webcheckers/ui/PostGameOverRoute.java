package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class handles the modeOptionsAsJSON for when a game is over or a player has resigned
 *
 * @author Team-E
 */
public class PostGameOverRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostGameOverRoute.class.getName());

    static final Message GAME_MSG = Message.info(String.format("You are playing a game of Webcheckers with %s", "an opponent"));

    final TemplateEngine templateEngine;

    private PlayerLobby lobby;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostGameOverRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.lobby = playerLobby;
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
        Player opponent = httpSession.attribute("opponent");

        Map<String, Object> vm = new HashMap<>();
        //k
        vm.put("title", "Webcheckers");

        // display a user message in the Game page
        vm.put("message", GAME_MSG);

        //variables for game
        vm.put("currentUser", myPlayer.getName());
        vm.put("viewMode", "PLAY");

        //set mode options as a map
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", myPlayer.getName() + " has captured all the pieces.");
        Gson gson = new Gson();
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

        vm.put("redPlayer", myPlayer.getName());
        vm.put("whitePlayer", opponent.getName());
        vm.put("activeColor", "RED");
        vm.put("board", lobby.getGame(myPlayer));


        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
