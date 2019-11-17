package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

/**
 * Unit test for PostSignInRoute
 * @author Matthew Klein
 */

@Tag("Ui-tier")
public class PostSignInRouteTest {

/**
 * Component under test (CuT)
 */

    private PostSignInRoute CuT;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Session session;
    private Request request;
    private Response response;

    /**
     * Setup mock classes to fill dependencies through
     * the test seam
     */
    @BeforeEach
    public void testSetup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby(new GameCenter());
        CuT = new PostSignInRoute(templateEngine, playerLobby);
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor(){
        assertNotNull(CuT.templateEngine, "The Template Engine is Null and should not be");
    }

    /**
     * Test the Post UI for adding the first player to the game
     */
    @Test
    public void first_player() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("id")).thenReturn("Name");
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("currentUser", request.queryParams("id"));
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("message", PostSignInRoute.WELCOME_MSG);
    }

    /**
     * Test the Post UI for adding more than one players to the game
     */
    @Test
    public void new_player() {
        //Arrange scenario
        //Add a player to the lobby with a different name
        Player other = new Player("Other");
        playerLobby.addPlayer(other);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("id")).thenReturn("Name");
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("currentUser", request.queryParams("id"));
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("message", PostSignInRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute("playerList", CuT.playerNames);
    }

    /**
     * Test the Post UI for ensuring two players with different names do not equal one another
     */
    @Test
    public void new_player_name() {
        //Arrange scenario
        //different player name
        Player other = new Player("Other");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("id")).thenReturn("Name");
        Player player = new Player(request.queryParams("id"));
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if names are not equal
        assertNotEquals(player.getName(),other.getName());
    }

    /**
     * Test the Post UI for ensuring that two players with the same name do equal each other
     */
    @Test
    public void same_player_name() {
        //Arrange scenario
        //same player name
        Player other = new Player("Name");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("id")).thenReturn("Name");
        Player player = new Player(request.queryParams("id"));
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if names are equal
        assertEquals(player,other);
    }

}
