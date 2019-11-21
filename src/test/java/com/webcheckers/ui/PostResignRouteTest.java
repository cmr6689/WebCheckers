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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostResignRoute
 * @author Matthew Klein
 */

@Tag("UI-tier")
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
        CuT = new PostResignRoute(playerLobby);
    }

    /**
     * Test the Post UI ending the game
     */
    @Test
    public void successful_resign() {
        Player p1 = new Player("Player");
        playerLobby.getGameCenter().newGame(p1, new Player("Opp"));

        Map<String, Object> vm = new HashMap<String, Object>();
        vm.put("isGameOver", true);
        vm.put("gameOverMessage", p1.getName() + " has resigned from the game. You are the winner!");

        when(session.attribute("player")).thenReturn(new Player("Player"));

        CuT.handle(request, response);
        assertTrue(playerLobby.getGameCenter().justEnded(p1));
        assertEquals(new Gson().toJson(vm), playerLobby.getGame(p1).getMap().get("modeOptionsAsJSON"));

        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not resign in the state you are in.");
    }

    /**
     * test resigning against the AI
     */
    @Test
    public void successful_resign_AI() {
        Player p1 = new Player("Player");
        playerLobby.getGameCenter().newGame(p1, new Player("AI Player"));

        Map<String, Object> vm = new HashMap<String, Object>();
        vm.put("isGameOver", true);
        vm.put("gameOverMessage", p1.getName() + " has resigned from the game. You are the winner!");

        when(session.attribute("player")).thenReturn(new Player("Player"));

        CuT.handle(request, response);
        assertNull(playerLobby.getGame(p1));
    }

    /**
     * test trying to resign when the other player already has
     */
    @Test
    public void game_already_ended() {
        Player p1 = new Player("Player");
        playerLobby.getGameCenter().newGame(p1, new Player("Opp"));
        playerLobby.getGameCenter().setJustEnded(p1, new Player("Opp"), true);

        when(session.attribute("player")).thenReturn(new Player("Player"));

        CuT.handle(request, response);
        assertTrue(playerLobby.getGameCenter().justEnded(p1));
    }
}
