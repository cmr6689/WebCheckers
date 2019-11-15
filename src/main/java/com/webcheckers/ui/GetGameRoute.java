package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Game page
 *
 * @author Team-E
 */
public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.GetGameRoute.class.getName());

    static final Message GAME_MSG = Message.info(String.format("You are playing a game of Webcheckers with %s", "an opponent"));

    final TemplateEngine templateEngine;

    private PlayerLobby lobby;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.lobby = playerLobby;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response){
        //TODO rewrite

        System.err.println("i was called");

        Session httpSession = request.session();

        Player myPlayer = httpSession.attribute("player");
        //player 1 part
        if (lobby.getGameCenter().getGame(myPlayer) == null) {
            final Player opponent;
            //loop through active players
            for (Player opp : lobby.getPlayers()) {
                //if clicked on opponent is real
                if (opp.equals(new Player(request.queryParams("opponent")))) {
                    //set the opponent
                    opponent = opp;
                    //set the opponent in the session
                    httpSession.attribute("opponent", opponent);
                    //create game

                    //if the opp is already in a game
                    if (opp.getInGame()) {
                        System.err.println("OH no the opp is already in a game");
                        final Message message = Message.error("This player is already in a game");

                        httpSession.attribute("message", message);
                        response.redirect("/");
                        return null;
                    }

                    lobby.getGameCenter().newGame(myPlayer, opponent);
                    lobby.getGameCenter().getGame(myPlayer).setIsActive(true);
                    //prevent access to end game code
                    lobby.getGameCenter().setJustEnded(false);

                    lobby.getGameCenter().getGame(myPlayer).getMap().put("currentUser", myPlayer.getName());
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("viewMode", "PLAY");
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("modeOptionsAsJSON", null);
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("redPlayer", myPlayer.getName());
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("whitePlayer", opponent.getName());
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("board", lobby.getGameCenter().getGame(myPlayer).getBoardView1());
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("title", "WebCheckers");
                    lobby.getGameCenter().getGame(myPlayer).getMap().put("message", GAME_MSG);

                    return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl"));
                }
            }
            //for the player that refreshes after the game ends
        } else if (lobby.getGameCenter().justEnded()) {
            lobby.getGameCenter().setJustEnded(false);
            //create temp map
            Map<String, Object> map = lobby.getGameCenter().getGame(myPlayer).getMap();
            //end game
            lobby.getGameCenter().endGame(lobby.getGameCenter().getGame(myPlayer).getPlayer1(), lobby.getGameCenter().getGame(myPlayer).getPlayer2());
            //display the temp map
            return templateEngine.render(new ModelAndView(map, "game.ftl"));

            //player 2 (white player)
        } else if (myPlayer.equals(lobby.getGameCenter().getGame(myPlayer).getPlayer2())) {
            lobby.getGameCenter().getGame(myPlayer).getMap().put("currentUser", lobby.getGameCenter().getGame(myPlayer).getPlayer2().getName());
            lobby.getGameCenter().getGame(myPlayer).getMap().put("board", lobby.getGameCenter().getGame(myPlayer).getBoardView2());
            lobby.getGameCenter().getGame(myPlayer).checkGameOver();
            //if game is over
            if (lobby.getGameCenter().getGame(myPlayer).getMap().get("modeOptionsAsJSON") != null) {
                //allow other player to remove the game
                lobby.getGameCenter().setJustEnded(true);
                return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl"));
            }
            return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl"));

            //player 1 (red player)
        } else if (myPlayer.equals(lobby.getGameCenter().getGame(myPlayer).getPlayer1())) {
            lobby.getGameCenter().getGame(myPlayer).getMap().put("currentUser", lobby.getGameCenter().getGame(myPlayer).getPlayer1().getName());
            lobby.getGameCenter().getGame(myPlayer).getMap().put("board", lobby.getGameCenter().getGame(myPlayer).getBoardView1());
            lobby.getGameCenter().getGame(myPlayer).checkGameOver();
            //if game is over
            if (lobby.getGameCenter().getGame(myPlayer).getMap().get("modeOptionsAsJSON") != null) {
                //allow other player to remove the game
                lobby.getGameCenter().setJustEnded(true);
                return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl"));
            }
            return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getMap(), "game.ftl"));
        }
        //response.redirect("/");
        System.err.println("Stop");
        return null;

    }
}

