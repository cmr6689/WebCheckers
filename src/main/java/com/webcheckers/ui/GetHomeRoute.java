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
    LOG.config("GetHomeRoute is invoked.");

    final Session httpSession = request.session();

    Player player = httpSession.attribute("player");

    if(player != null) {
      if (!playerLobby.getGameCenter().justEnded(player)) {
        if (playerLobby.getGame(player) != null && playerLobby.getGame(player).isActive()) {
          if (player.equals(playerLobby.getGameCenter().getGame(player).getPlayer2())) {
            LOG.config("Game found!");
            response.redirect("/game");
          }
        }
      }
    }

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    if(httpSession.attribute("message") == null)
      vm.put("message", WELCOME_MSG);
    else
      vm.put("message", httpSession.attribute("message"));


      //Message message = Message.info(playerLobby.players.toString());
      //vm.put("message",message);

    if(player != null)
      vm.put("currentUser", player.getName());

    if (playerLobby.getPlayers().size() >= 1) {
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
