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

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  private static final Message INVALID_NAME = Message.info("The name you have chosen is already taken");

  private final TemplateEngine templateEngine;

  private PlayerLobby playerLobby;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("PostSignInRoute is initialized.");

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
    Map<String, Object> vm = new HashMap<>();
    System.out.println(request.queryParams("id"));
    //add a sign-in id to a player
    Player player = new Player(request.queryParams("id"));

    Session httpSession = request.session();
    final PlayerLobby playerLobby = httpSession.attribute("playerServices");
    playerLobby.addPlayer(player);

    if(playerLobby.addPlayer(player) == 0){
      vm.put("message", INVALID_NAME);
    }

    //Check to see if another player has the same name

    LOG.finer("PostSignInRoute is invoked.");
    //

    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    vm.put("currentUser", request.queryParams("id"));
    if (playerLobby.players.size() > 1) {
      ArrayList<String> playerNames = new ArrayList<>();
      for (Player player1 : playerLobby.players) {
        playerNames.add(player1.getName());
      }
      vm.put("playerList", playerNames);
    }
    

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
