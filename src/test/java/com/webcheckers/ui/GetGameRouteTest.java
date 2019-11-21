package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
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
        this.lobby = new PlayerLobby(new GameCenter());
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
        Player myPlayer = new Player("Player");
        when(session.attribute("Player")).thenReturn(myPlayer);
        Player opponent = new Player("Opp");
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.getGameCenter().newGame(myPlayer, opponent);
        lobby.getGameCenter().getGame(myPlayer).setIsActive(true);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Cut.handle(request, response);
        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
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
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        Cut.handle(request, response);
        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("whitePlayer", fakeOpp.getName());
        testHelper.assertViewModelAttribute("message", GetGameRoute.GAME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

    /**
     * Make sure the viewMode and modeOptionsAsJSON are valid and correct
     */

    @Test void exsist(){
        when(session.attribute("player")).thenReturn(new Player("Player"));
    }

    /**
     * test the game mode
     */
    @Test
    public void testModes() {
        when(session.attribute("player")).thenReturn(new Player("Player"));
        assertNull(Cut.handle(request, response));
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player fakeOpp = new Player("Opp");
        lobby.addPlayer(fakeOpp);
        when(request.queryParams("opponent")).thenReturn("Opp");
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        Cut.handle(request, response);
        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");

    }

    /**
     * test if clicked on opponent is already in a game
     */
    @Test
    public void opponentInGame() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player opp = new Player("Opp");
        lobby.addPlayer(opp);
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.getGameCenter().newGame(opp, new Player("fakePlayer"));
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        assertNull(Cut.handle(request, response));
        assertTrue(opp.getInGame());
        Cut.handle(request, response);
        assertNull(session.attribute("message"));
    }

    /**
     * test if the game has been ended due to win conditions
     */
    @Test
    public void testJustEnded() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player opp = new Player("Opp");
        lobby.addPlayer(opp);
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.getGameCenter().newGame(new Player("Player"), opp);
        lobby.getGameCenter().setJustEnded(new Player("Player"), opp, true);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Map<String, Object> map = new HashMap<>();
        map.put("currentUser", "Player");
        map.put("board", lobby.getGame(new Player("Player")).getBoardView1());

        Cut.handle(request, response);
        testHelper.assertViewModelAttribute("currentUser", map.get("currentUser"));
        testHelper.assertViewModelAttribute("board", map.get("board"));
        assertNull(lobby.getGame(new Player("Player")));
        assertTrue(lobby.getGameCenter().justEnded(new Player("Player")));

    }

    /**
     * test if the game is properly over
     */
    @Test
    public void testGameOver() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute("player")).thenReturn(new Player("Player"));
        Player opp = new Player("Opp");
        lobby.addPlayer(opp);
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.getGameCenter().newGame(new Player("Player"), opp);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Map<String, Object> map = new HashMap<>();
        map.put("isGameOver", true);
        map.put("gameOverMessage", "message");
        lobby.getGame(opp).getMap().put("modeOptionsAsJSON", new Gson().toJson(map));

        Cut.handle(request, response);
        testHelper.assertViewModelAttribute("currentUser", "Player");
        testHelper.assertViewModelAttribute("board", lobby.getGame(opp).getBoardView1());
        assertEquals(new Player("Player"), lobby.getGame(opp).getPlayer1());
        assertNotNull(lobby.getGame(opp).getMap().get("modeOptionsAsJSON"));
        assertTrue(lobby.getGameCenter().justEnded(new Player("Player")));

    }

    /**
     * test if the game ends for the non-active player
     */
    @Test
    public void testGameOverP2() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute("player")).thenReturn(new Player("Opp"));
        Player opp = new Player("Opp");
        lobby.addPlayer(opp);
        when(request.queryParams("opponent")).thenReturn("Opp");
        lobby.getGameCenter().newGame(opp, new Player("Player"));
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Map<String, Object> map = new HashMap<>();
        map.put("isGameOver", true);
        map.put("gameOverMessage", "message");
        lobby.getGame(opp).getMap().put("modeOptionsAsJSON", new Gson().toJson(map));

        Cut.handle(request, response);
        testHelper.assertViewModelAttribute("currentUser", "Opp");
        testHelper.assertViewModelAttribute("board", lobby.getGame(opp).getBoardView1());
        assertEquals(opp, lobby.getGame(opp).getPlayer1());
        assertNotNull(lobby.getGame(opp).getMap().get("modeOptionsAsJSON"));
        assertTrue(lobby.getGameCenter().justEnded(opp));
    }
}
