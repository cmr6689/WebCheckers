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
 * Unit test for PostSignOutRoute
 * @author Matthew Klein
 */

@Tag("Ui-tier")
public class PostSignOutRouteTest {
    /**
     * Component under test (CuT)
     */

    private PostSignOutRoute CuT;
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
        playerLobby = new PlayerLobby(new GameCenter());
        CuT = new PostSignOutRoute(playerLobby);
    }

    /**
     * Ensure that player lobby names are valid
     */
    @Test
    public void ctor(){
        assertFalse(playerLobby.getInvalidName());
    }

    /**
     * Test the Post UI for removing player
     */
    @Test
    public void redirect() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(request.queryParams("signout")).thenReturn("Name");
        Player player = new Player(request.queryParams("signout"));
        playerLobby.addPlayer(player);
        playerLobby.getPlayers().remove(player);

        // Invoke the test
        CuT.handle(request, response);

        assertFalse(playerLobby.getInvalidName());

    }

    @Test
    public void signOutInGame() {
        Player player = new Player("Player");
        when(request.queryParams("signout")).thenReturn("Player");
        playerLobby.getGameCenter().newGame(player, new Player("Opp"));
        CuT.handle(request, response);
        assertNotNull(playerLobby.getGame(player).getMap().get("modeOptionsAsJSON"));
        assertTrue(playerLobby.getGameCenter().justEnded(player));
    }

    @Test
    public void signOutInGameAI() {
        Player player = new Player("Player");
        when(request.queryParams("signout")).thenReturn("Player");
        playerLobby.getGameCenter().newGame(player, new Player("AI Player"));
        CuT.handle(request, response);
        assertNull(playerLobby.getGame(player));
    }
}
