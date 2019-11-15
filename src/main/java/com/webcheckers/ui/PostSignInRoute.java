package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * The UI Controller to POST the Sign In page.
 *
 * @author Team-E
 */
public class PostSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  ArrayList<String> playerNames;

  final TemplateEngine templateEngine;

  PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = playerLobby;
  }

  /**
   * Render the WebCheckers Sign In page.
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
    LOG.config("PostSignInRoute is invoked");
    Map<String, Object> vm = new HashMap<>();
    //add a sign-in id to a player
    Player player = new Player(request.queryParams("id"));

    //Check to see if another player has the same name
    Session httpSession = request.session();
    if(!playerLobby.addPlayer(player)) {
      response.redirect("/signin");
      playerLobby.setInvalidName(true);
    } else {
      httpSession.attribute("player", player);
    }
    //
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    vm.put("currentUser", request.queryParams("id"));
    if (playerLobby.getPlayers().size() > 1) {
      playerNames = new ArrayList<>();
      for (Player player1 : playerLobby.getPlayers()) {
        playerNames.add(player1.getName());
      }
      vm.put("playerList", playerNames);
    }
    

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
