package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
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
    private GameData gameData;

    /**
     * Setting up mock classes to fulfill dependencies throughout the tests
     */
    @BeforeEach
    public void setup() {
        this.request = mock(Request.class);
        this.session = mock(Session.class);
        this.gameData = mock(GameData.class);
        when(request.session()).thenReturn(session);
        this.response = mock(Response.class);
        this.engine = mock(TemplateEngine.class);
        this.lobby = new PlayerLobby(new GameCenter());
        this.Cut = new GetGameRoute(engine, lobby, gameData);
    }

    /**
     * Make sure engine in non-null
     */
    @Test
    public void nonNullEngine() {
        assertNotNull(Cut.templateEngine, "Template Engine is Null!");
    }

    /**
     * Invalid opponent not clicked on
     */
    @Test
    public void noOpponent() {
        when(session.attribute("player")).thenReturn(new Player("Player"));

        assertNull(Cut.handle(request, response));
    }

    /**
     * Test to see if a player is actually signed in when accessing the game
     */
    @Test
    public void playerSignedIn() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player fakeOpp = new Player("Opp");
        lobby.addPlayer(fakeOpp);
        when(request.queryParams("opponent")).thenReturn("Opp");
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

        testHelper.assertViewModelAttribute("currentUser", request.queryParams("Player"));
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
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player fakeOpp = new Player("Opp");
        lobby.addPlayer(fakeOpp);

        when(request.queryParams("opponent")).thenReturn("Opp");
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

        testHelper.assertViewModelAttribute("redPlayer", request.queryParams("Player"));
        testHelper.assertViewModelAttribute("whitePlayer", fakeOpp.getName());
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

    /**
     * Make sure the viewMode and modeOptionsAsJSON are valid and correct
     */

    @Test void exsist(){
        assertNotNull(Cut.handle(request, response));
    }

    @Test
    public void testModes() {

        assertNull(Cut.handle(request, response));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player fakeOpp = new Player("Opp");
        lobby.addPlayer(fakeOpp);
        when(request.queryParams("opponent")).thenReturn("Opp");
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

        testHelper.assertViewModelAttribute("currentUser", request.queryParams("Player"));
        testHelper.assertViewModelAttribute("title", "Webcheckers");
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("modeOptionsAsJSON", null);
        testHelper.assertViewModelAttribute("redPlayer", request.queryParams("Player"));
        testHelper.assertViewModelAttribute("whitePlayer", fakeOpp.getName());
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);

        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");

    }
}
