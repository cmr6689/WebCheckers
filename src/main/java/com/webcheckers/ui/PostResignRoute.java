package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /resign and responds with a json message
 *
 * @author Team-E
 */
public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    static final Message RESIGN_MSG = Message.info("You have resigned from the game");

    final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param templateEngine the template engine
     * @param playerLobby the lobby of all the players
     */
    public PostResignRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        System.out.println("RESIGNED");

        LOG.finer("PostResignRoute is invoked.");

        Session httpSession = request.session();
        Player p1 = httpSession.attribute("player");
        vm.put("isGameOver", true);
        vm.put("gameOverMessage", p1.getName() + " has resigned from the game.");
        playerLobby.getGameCenter().getGame(p1).getMap().put("modeOptionsAsJSON", new Gson().toJson(vm));
        playerLobby.getGameCenter().setJustEnded(true);

        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not resign in the state you are in.");

        // render the View
        return gson.toJson(message);
    }
}
