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

    private GameData gameData;

    private boolean initial = true;

    private Player opponent;

    private boolean turn = true;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameData gameData) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.lobby = playerLobby;
        this.gameData = gameData;
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
                    lobby.getGameCenter().newGame(myPlayer, opponent);

                    gameData.setCurrentUser(myPlayer);
                    gameData.setViewMode("PLAY");
                    gameData.setModeOptionsAsJSON(null);
                    gameData.setRedPlayer(myPlayer);
                    gameData.setWhitePlayer(opponent);
                    gameData.setActiveColor("RED");
                    gameData.setBoard(lobby.getGameCenter().getGame(myPlayer).getBoardView1());
                    gameData.dataSetup();
                    gameData.getVm().put("title", "Webcheckers");
                    // display a user message in the Game page
                    gameData.getVm().put("message", GAME_MSG);

                    lobby.getGameCenter().getGame(myPlayer).getGameData().setVm(gameData.getVm());

                    return templateEngine.render(new ModelAndView(gameData.getVm(), "game.ftl"));
                }
            }
        }
        //player 2 part
        else {
            lobby.getGameCenter().getGame(myPlayer).getGameData().getVm().put("currentUser", lobby.getGameCenter().getGame(myPlayer).getPlayer1().getName());
            lobby.getGameCenter().getGame(myPlayer).getGameData().getVm().put("board", lobby.getGameCenter().getGame(myPlayer).getBoardView2());
            lobby.getGameCenter().getGame(myPlayer).setIsActive(true);
            return templateEngine.render(new ModelAndView(lobby.getGameCenter().getGame(myPlayer).getGameData().getVm(), "game.ftl"));
        }
        //response.redirect("/");
        System.err.println("Stop");
        return null;

    }
}

