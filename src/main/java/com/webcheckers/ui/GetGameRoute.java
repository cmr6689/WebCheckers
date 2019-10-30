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

public class GetGameRoute implements Route{

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.GetGameRoute.class.getName());

    static final Message GAME_MSG = Message.info(String.format("You are playing a game of Webcheckers with %s", "an opponent"));

    final TemplateEngine templateEngine;

    private PlayerLobby lobby;

    private GameData gameData;

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
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response){

        Session httpSession = request.session();

        Player myPlayer = httpSession.attribute("player");

        final Player opponent;
        Player fakeOpp = new Player(request.queryParams("opponent"));
        try {
            if (!fakeOpp.equals(lobby.getGame(myPlayer).getPlayer1())) {
                fakeOpp.equals(lobby.getGame(myPlayer).getPlayer2());
            }
        } catch (NullPointerException e){
            //System.err.println("RIP");
        }
        for (Player opp : lobby.getPlayers()) {
            if (opp.equals(fakeOpp)) {
                opponent = opp;
                httpSession.attribute("opponent", opponent);
                lobby.getGameCenter().newGame(myPlayer, opponent);
                //
                Map<String, Object> vm = new HashMap<>();
                //k
                vm.put("title", "Webcheckers");

                // display a user message in the Game page
                vm.put("message", GAME_MSG);

                //variables for game
//                vm.put("currentUser", myPlayer.getName());
//                vm.put("viewMode", "PLAY");
//                vm.put("modeOptionsAsJSON!", null);
//                vm.put("redPlayer", myPlayer.getName());
//                vm.put("whitePlayer", opponent.getName());
//                vm.put("activeColor", "RED");
//                vm.put("board", game.getGame(myPlayer));
                gameData.setVm(vm);
                gameData.setCurrentUser(myPlayer);
                gameData.setViewMode("PLAY");
                gameData.setModeOptionsAsJSON(null);
                gameData.setRedPlayer(myPlayer);
                gameData.setWhitePlayer(opponent);
                gameData.setActiveColor("RED");
                gameData.setBoard(lobby.getGame(myPlayer));
                gameData.dataSetup();
                vm = gameData.getVm();
                lobby.setMap(vm);

                if(!lobby.getGame(myPlayer).isActive()){
                    response.redirect("/home");
                }
                // render the View
                return templateEngine.render(new ModelAndView(vm, "game.ftl"));
            }
        }
        //response.redirect("/");
        System.err.println("Stop");
        return null;
    }
}

