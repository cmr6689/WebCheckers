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
        Session httpSession = request.session();
        Player p1 = httpSession.attribute("player");

        LOG.config("PostResignRoute is invoked by " + p1.getName() + ".");

        //if player 2 resigns
        if (playerLobby.getGameCenter().justEnded(p1)) {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.ERROR);
            message2.setText("Opponent has already resigned. Submit a move to end the game.");
            return gson.toJson(message2);
        }

        Map<String, Object> vm = new HashMap<>();
        vm.put("isGameOver", true);
        vm.put("gameOverMessage", p1.getName() + " has resigned from the game. You are the winner!");
        playerLobby.getGameCenter().getGame(p1).getMap().put("modeOptionsAsJSON", new Gson().toJson(vm));
        //if resigning against AI just end the game
        if (playerLobby.getGameCenter().getGame(p1).getPlayer2().getName().equals("AI Player")) {
            playerLobby.getGameCenter().endGame(p1, new Player("AI Player"));
        } else {
            playerLobby.getGameCenter().setJustEnded(playerLobby.getGame(p1).getPlayer1(), playerLobby.getGame(p1).getPlayer2(), true);
        }

        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not resign in the state you are in.");

        // render the View
        return gson.toJson(message);
    }
}
