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

  public WebServer webServer;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine, WebServer webServer) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("PostSignInRoute is initialized.");
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

//    PlayerLobby lobby = new PlayerLobby();
//    ArrayList<Player> lobbyList = lobby.getPlayers();


    //Check to see if another player has the same name

    /*
    for(Player person : lobbyList){
      if(!player.getName().equals(person.getName())){
        lobbyList.add(player);
      }else{
        vm.put("message",INVALID_NAME);
      }
    }*/

    LOG.finer("PostSignInRoute is invoked.");
    //

    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    vm.put("currentUser", request.queryParams("id"));
    

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
