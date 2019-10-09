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

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;

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
    //

    final Session httpSession = request.session();

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    final PlayerLobby playerLobby;
    if(httpSession.attribute("playerServices") == null){
      playerLobby = new PlayerLobby();
      httpSession.attribute("playerServices", playerLobby);
    }else{
      playerLobby = httpSession.attribute("playerServices");

      //Message message = Message.info(playerLobby.players.toString());
      //vm.put("message",message);

    }

    //TODO - this is where it refreshes and makes every user the newest
    if(playerLobby.getPlayers().size() != 0) {
      if (vm.get("currentUser.name") == null) {
        System.out.println("Current username = " + vm.get("currentUser.name"));

      }
    }

    Player player = httpSession.attribute("player");
    if(player != null)
      vm.put("currentUser", player.getName());

    if (playerLobby.getPlayers().size() > 1) {
      ArrayList<String> playerNames = new ArrayList<>();
      for (Player player1 : playerLobby.getPlayers()) {
        playerNames.add(player1.getName());
      }
      vm.put("playerList", playerNames);
    }



    //vm.put("playerList", playerLobby.players);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
