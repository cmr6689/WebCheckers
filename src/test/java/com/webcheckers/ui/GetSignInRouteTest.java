package com.webcheckers.ui;


import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetSignInRoute} component.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Evan Price</a>
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    /**
     * The component-under-test (CuT).
     */
    private GetSignInRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby(new GameCenter());

        // create a unique CuT for each test
        CuT = new GetSignInRoute(engine,playerLobby);
    }

    /**
     * Make sure engine in non-null
     */
    @Test
    public void nonNullEngine() {
        assertNotNull(CuT.templateEngine, "Template Engine is Null!");
    }

    /**
     * Test a case where the name provided is invalid
     */
    @Test
    public void invalid_name_alphanumeric() {
        //create player with invalid name
        Player player1 = new Player("$$$$");
        //Add a player to a new empty lobby
        playerLobby.addPlayer(player1);
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(CuT.templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if name is invalid
        testHelper.assertViewModelAttribute("title", "Sign In");
        testHelper.assertViewModelAttribute("message", GetSignInRoute.INVALID_NAME);
    }

    /**
     * Test a case where the name provided is used twice
     */
    @Test
    public void invalid_name_double() {
        //create players with the same name
        Player player1 = new Player("Test");
        Player player2 = new Player("Test");
        //add players to the lobby
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(CuT.templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if names are equal
        testHelper.assertViewModelAttribute("title", "Sign In");
        testHelper.assertViewModelAttribute("message", GetSignInRoute.INVALID_NAME);
        assertEquals(player1.getName(),player2.getName());
    }

    /**
     * Test that the players name is valid
     */
    @Test
    public void valid_name() {
        //cretate players with different valid names
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");
        //add players to lobby
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(CuT.templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if message is right
        testHelper.assertViewModelAttribute("title", "Sign In");
        testHelper.assertViewModelAttribute("message", GetSignInRoute.WELCOME_MSG);
    }
}
