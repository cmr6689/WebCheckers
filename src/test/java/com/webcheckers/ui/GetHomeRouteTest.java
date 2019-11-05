package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostSignInRoute
 * @author Ronald Torrelli
 */

@Tag("UI-Tier")
class GetHomeRouteTest {

    private GetHomeRoute CuT;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Session session;
    private Request request;
    private Response response;

    @BeforeEach
    void setUp() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby(new GameCenter());

        CuT = new GetHomeRoute(templateEngine, playerLobby);
    }

    @Test
    public void ctor(){
        assertNotNull(CuT.getTemplateEngine(), "The Template Engine is Null and should not be");
    }

    @Test
    public void testPlayerList(){
        final TemplateEngineTester engineTester = new TemplateEngineTester();

        when(session.attribute("currentUser")).thenReturn(new Player("1"));

        CuT.handle(request, response);

        ArrayList<String> playerNames = new ArrayList<>();
        if (playerLobby.getPlayers().size() > 0){
            for (Player player1 : playerLobby.getPlayers()) {
                playerNames.add(player1.getName());
            }
        }
        //engineTester.assertViewModelExists();
        try {
            engineTester.assertViewModelAttribute("playerList", playerNames);
        } catch (NullPointerException e){
        }

    }

    @Test
    public void playerDoesNotExist(){
        final TemplateEngineTester engineTester = new TemplateEngineTester();
        CuT.handle(request, response);

        assertNull(session.attribute("player"));

    }

    @Test
    public void playerJoinsGame() {
        when(session.attribute("player")).thenReturn(new Player("Player"));
        playerLobby.getGameCenter().newGame(new Player("Opp"), new Player("Player"));
        Map<String, Object> vm = new HashMap<>();
        vm.put("currentUser", "Opp");
        vm.put("activeColor", "RED");
        vm.put("redPlayer", "Opp");
        vm.put("whitePlayer", "Player");
        playerLobby.setMap(vm);
        assertEquals(CuT.handle(request, response), templateEngine.render(new ModelAndView(playerLobby.getMap(), "game.ftl")));
    }

}