package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static spark.route.HttpMethod.post;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author Team-E
 */
public class GetSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  static final Message WELCOME_MSG = Message.info("Please Sign-in with a valid user ID");

  //message for the user if they input an invalid name
  static final Message INVALID_NAME = Message.error("The name you have chosen is already taken or contains non-alphanumeric or is above 100 characters");

  final TemplateEngine templateEngine;

  private PlayerLobby playerlobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerlobby = playerLobby;
    playerLobby.setInvalidName(false);
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
   *   the rendered HTML for the Sign In page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.config("GetSignInRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign In");

    // display a user message in the Home page
    if (!playerlobby.getInvalidName()) {
      vm.put("message", WELCOME_MSG);
    } else{
      vm.put("message", INVALID_NAME);
      playerlobby.setInvalidName(false);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
