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

    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    private GameData gameData;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param gameData the vm map data of the game
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameData gameData) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
        this.gameData = gameData;
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
         LOG.finer("PostCheckTurn is invoked.");

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        System.err.println(gameData.getVm().get("activeColor"));

        if(playerLobby.getGame(myPlayer).getPlayer1().equals(gameData.getVm().get("currentUser"))) {
            gameData.setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());
            gameData.setActiveColor("RED");
        }else{
            gameData.setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());
            gameData.setActiveColor("WHITE");
        }

        if (gameData.getVm().get("currentUser").equals(myPlayer.getName())) {

            ResponseMessage message1 = new ResponseMessage();
            message1.setType(ResponseMessage.MessageType.INFO);
            message1.setText("It is your turn.");
            // render the View
            gameData.dataSetup();
            playerLobby.setMap(gameData.getVm());

            return gson.toJson(message1);
        } else {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("It is not your turn.");
            // render the View

            //System.err.println("postcheck turn, not my turn  : " + myPlayer.getName());

            gameData.dataSetup();
            playerLobby.setMap(gameData.getVm());

            return gson.toJson(message2);
        }
    }
}