package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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
        Player player = new Player(request.queryParams("signout"));
        this.playerlobby.getPlayers().remove(player);
        playerlobby.setInvalidName(false);
        response.redirect("/signin");
        return null;
    }
}
