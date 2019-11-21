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
 * The UI Controller to POST the Sign Out page.
 *
 * @author Team-E
 */
public class PostSignOutRoute implements Route {

    private PlayerLobby playerlobby;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby the player lobby containing all the players
     */
    public PostSignOutRoute(PlayerLobby playerLobby) {
        this.playerlobby = playerLobby;
        playerLobby.setInvalidName(false);
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        httpSession.removeAttribute("player");
        Player player = new Player(request.queryParams("signout"));
        if (playerlobby.getGame(player) != null) {
            if (playerlobby.getGame(player).getPlayer2().getName().equals("AI Player")) {
                playerlobby.getGameCenter().endGame(playerlobby.getGame(player).getPlayer1(), new Player("AI Player"));
            } else {
                final Map<String, Object> modeOptions = new HashMap<>(2);
                Gson gson = new Gson();
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage",  "Opponent has signed out, you are the winner!");
                playerlobby.getGameCenter().getGame(player).getMap().put("modeOptionsAsJSON", gson.toJson(modeOptions));
                playerlobby.getGameCenter().setJustEnded(playerlobby.getGame(player).getPlayer1(), playerlobby.getGame(player).getPlayer2(), true);
            }
        }
        this.playerlobby.getPlayers().remove(player);
        playerlobby.setInvalidName(false);
        response.redirect("/");
        return null;
    }
}
