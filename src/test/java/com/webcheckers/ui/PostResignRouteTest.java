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
 * Unit test for PostResignRoute
 * @author Matthew Klein
 */

@Tag("Ui-tier")
public class PostResignRouteTest {
    /**
     * Component under test (CuT)
     */

    private PostResignRoute CuT;
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
        CuT = new PostResignRoute(templateEngine, playerLobby);
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor(){
        assertNotNull(CuT.templateEngine, "The Template Engine is Null and should not be");
    }

    /**
     * Test the Post UI ending the game
     */
    @Test
    public void successful_resign() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("gameID")).thenReturn("Game");
        final Session httpSession = request.session();
        Player p1 = httpSession.attribute("player");
        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not resign in the state you are in.");
        //playerLobby.getGameCenter().endGame(p1);
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        //Check if game is over
        assertEquals(playerLobby.getGameCenter().gameIsActive(playerLobby.getGame(p1)),false);
    }

    @Test
    void handle() {
    }
}
