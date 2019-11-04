package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostCheckTurnRoute
 * @author Cameron Riu
 */
@Tag("Ui-tier")
public class PostGameOverRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostGameOverRoute CuT;
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
        CuT = new PostGameOverRoute(templateEngine, playerLobby);
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor() {
        assertNotNull(CuT.templateEngine, "The Template Engine is Null and should not be");
    }

    /**
     * Make sure the mode options were updated when game ends
     */
    @Test
    public void modeOptionsUpdated() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        final Session httpSession = request.session();
        when(session.attribute("player")).thenReturn(new Player("Player"));
        when(session.attribute("opponent")).thenReturn(new Player("Opp"));
        Player p1 = httpSession.attribute("player");

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", p1.getName() + " has captured all the pieces.");
        Gson gson = new Gson();


        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("modeOptionsAsJSON", gson.toJson(modeOptions));
    }
}
