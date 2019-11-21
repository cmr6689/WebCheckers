package com.webcheckers.ui;

import com.google.gson.Gson;
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
 * Unit test for PostCheckTurnRoute
 * @author Cameron Riu
 */
@Tag("Ui-tier")
public class PostCheckTurnRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostCheckTurnRoute CuT;
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
        CuT = new PostCheckTurnRoute(templateEngine, playerLobby);
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor(){
        assertNotNull(CuT.templateEngine, "The Template Engine is Null and should not be");
    }

    /**
     * Check if it is the players turn
     */
    @Test
    public void isMyTurn() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("gameID")).thenReturn("Game");
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player fakeOpp = new Player("Opp");
        when(request.queryParams("opponent")).thenReturn("Opp");

        ResponseMessage message1 = new ResponseMessage();
        message1.setType(ResponseMessage.MessageType.INFO);
        message1.setText("false");
        Gson gson = new Gson();

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        assertEquals(gson.toJson(message1), CuT.handle(request, response));
    }

    /**
     * Check if it is not the players turn
     */
    @Test
    public void isNotMyTurn() {
        Gson gson = new Gson();

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("true");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        Player p1 = new Player("1");
        Player AI = new Player("AI");
        AI.setAI();
        playerLobby.getGameCenter().newGame(p1,AI);
        assertNotNull(playerLobby.getGameCenter().getGame(p1));
        //when(request.queryParams("gameID")).thenReturn("Game");
        when(session.attribute("player")).thenReturn(p1);
        //playerLobby.getGameCenter().setJustEnded(p1,AI,true);

        ResponseMessage message1 = new ResponseMessage();
        message1.setType(ResponseMessage.MessageType.INFO);
        message1.setText("false");

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }

    /**
     * Check if it is not the players turn
     */
    @Test
    public void lastTest() {
        Gson gson = new Gson();

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("true");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        Player p1 = new Player("1");
        Player AI = new Player("AI");
        AI.setAI();
        playerLobby.getGameCenter().newGame(p1,AI);
        assertNotNull(playerLobby.getGameCenter().getGame(p1));
        //when(request.queryParams("gameID")).thenReturn("Game");
        when(session.attribute("player")).thenReturn(p1);
        playerLobby.getGameCenter().setJustEnded(p1,AI,true);

        ResponseMessage message1 = new ResponseMessage();
        message1.setType(ResponseMessage.MessageType.INFO);
        message1.setText("false");

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }
}
