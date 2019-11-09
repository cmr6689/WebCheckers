package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.post;
import static spark.Spark.redirect;
import static spark.route.HttpMethod.get;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;

  public TemplateEngine getTemplateEngine() {
    return templateEngine;
  }

  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");

    this.playerLobby = playerLobby;
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");

    final Session httpSession = request.session();

    Player player = httpSession.attribute("player");

    if(player != null) {
      if (playerLobby.getGame(player) != null) {
          if (player == playerLobby.getGameCenter().getGame(player).getPlayer2()) {
            //playerLobby.getMap().put("currentUser", playerLobby.getGame(player).getPlayer1());
            //playerLobby.getMap().put("board", playerLobby.getGame(player).getBoardView2());
            //playerLobby.getGame(player).setIsActive(true);
            playerLobby.getGameCenter().getGame(player).getGameData().getVm().put("currentUser", playerLobby.getGameCenter().getGame(player).getPlayer1());
            playerLobby.getGameCenter().getGame(player).getGameData().getVm().put("board", playerLobby.getGameCenter().getGame(player).getBoardView2());
            playerLobby.getGameCenter().getGame(player).setIsActive(true);
            playerLobby.getGameCenter().getGame(player).getGameData().dataSetup();
            System.err.println("Game found!");
            response.redirect("/game");
            //return null;
            //return templateEngine.render(new ModelAndView(playerLobby.getMap(), "game.ftl"));
          }
      }
    }

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);


      //Message message = Message.info(playerLobby.players.toString());
      //vm.put("message",message);

    if(player != null)
      vm.put("currentUser", player.getName());

    if (playerLobby.getPlayers().size() > 0) {
      ArrayList<String> playerNames = new ArrayList<>();
      for (Player player1 : playerLobby.getAvaPlayers()) {
        playerNames.add(player1.getName());
      }
      vm.put("playerList", playerNames);
    }


    //vm.put("playerList", playerLobby.players);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
