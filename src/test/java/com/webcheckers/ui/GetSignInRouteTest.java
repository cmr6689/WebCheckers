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
        playerLobby = new PlayerLobby();

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
    public void invalid_name() {
        // Arrange the test scenario: The session holds no names.
        final PlayerLobby lobby1 = new PlayerLobby();
        Player test1 = new Player("$$$$");
        lobby1.addPlayer(test1);

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test (ignore the output)
        CuT.handle(request, response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        when(request.session().attribute("player")).thenReturn(new Player("$$$$"));
        testHelper.assertViewModelAttributeIsAbsent("$$$$");
        testHelper.assertViewModelAttribute("message", GetSignInRoute.INVALID_NAME);
    }

    /**
     * Test that the players name is valid
     */
    @Test
    public void valid_name() {
        // Arrange the test scenario: The session holds no names.
        final PlayerLobby lobby2 = new PlayerLobby();

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test (ignore the output)
        CuT.handle(request, response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        //   * test view name
        //testHelper.assertViewName(GetSignInRoute.);
    }
}
