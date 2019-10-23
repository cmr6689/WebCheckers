package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetGameRoute} component.
 *
 * @author <a href='mailto:cmr6689@rit.edu'>Cameron Riu</a>
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    private GetGameRoute Cut;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby lobby;

    /**
     * Setting up mock classes to fulfill dependencies throughout the tests
     */
    @BeforeEach
    public void setup() {
        this.request = mock(Request.class);
        this.session = mock(Session.class);
        when(request.session()).thenReturn(session);
        this.response = mock(Response.class);
        this.engine = mock(TemplateEngine.class);
        this.lobby = new PlayerLobby();
        this.Cut = new GetGameRoute(engine, lobby);
    }

    /**
     * Make sure engine in non-null
     */
    @Test
    public void nonNullEngine() {
        assertNotNull(Cut.templateEngine, "Template Engine is Null!");
    }

    /**
     * Test to see if a player is actually signed in when accessing the game
     */
    @Test
    public void playerSignedIn() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.session().attribute("player")).thenReturn(new Player("Player"));
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.addPlayer(new Player("Opp"));
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        Cut.handle(request, response);

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("currentUser", new Player("Player").getName());
        testHelper.assertViewModelAttribute("title", "Webcheckers");
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

    /**
     * Make sure both players are assigned the right color
     */
    @Test
    public void playerColor() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.session().attribute("player")).thenReturn(new Player("Player"));
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.addPlayer(new Player("Opp"));
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        Cut.handle(request, response);

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("redPlayer", new Player("Player").getName());
        testHelper.assertViewModelAttribute("whitePlayer", new Player("Opp").getName());
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

    /**
     * Make sure the viewMode and modeOptionsAsJSON are valid and correct
     */
    @Test
    public void testModes() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.session().attribute("player")).thenReturn(new Player("Player"));
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.addPlayer(new Player("Opp"));
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        Cut.handle(request, response);

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("currentUser", new Player("Player").getName());
        testHelper.assertViewModelAttribute("title", "Webcheckers");
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("modeOptionsAsJSON", null);
        testHelper.assertViewModelAttribute("redPlayer", new Player("Player").getName());
        testHelper.assertViewModelAttribute("whitePlayer", new Player("Opp").getName());
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

}
