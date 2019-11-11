package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Route that handles the ajax call of checking whose turn it is while it is currently not your turn.
 *
 * @author Team-E
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());

    public final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
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
        System.out.println("Called Check");
         LOG.finer("PostCheckTurn is invoked.");
         //TODO check if you are the active color other wise refresh

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        //Player opp = httpSession.attribute("opponent");

        System.err.println(playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor"));



        if (!playerLobby.getGameCenter().getGame(myPlayer).getActivePlayer().equals(myPlayer.getName())) {

            ResponseMessage message1 = new ResponseMessage();
            message1.setType(ResponseMessage.MessageType.INFO);
            message1.setText("false");

            //playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", opp.getName());

            if(myPlayer.equals(playerLobby.getGameCenter().getGame(myPlayer).getPlayer1()))
                playerLobby.getGameCenter().getGame(myPlayer).getMap().put("board", playerLobby.getGame(myPlayer).getBoardView1());
            else
                playerLobby.getGameCenter().getGame(myPlayer).getMap().put("board", playerLobby.getGame(myPlayer).getBoardView2());

            playerLobby.getGame(myPlayer).setIsActive(true);
            Spark.get("/game", (req, res) -> templateEngine.render(new ModelAndView(playerLobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl")));

            return gson.toJson(message1);
        } else {
            //playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", myPlayer.getName());
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("true");
            return gson.toJson(message2);
        }
    }
}