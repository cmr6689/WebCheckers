package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for PostSubmitTurnRoute
 */
@Tag("UI-tier")
class PostSubmitTurnRouteTest {

    /**
     * Component under test (CuT)
     */
    private PostSubmitTurnRoute CuT;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Session session;
    private Request request;
    private Response response;
    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Space> spaces = new ArrayList<>();

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
        CuT = new PostSubmitTurnRoute(playerLobby);

        when(session.attribute("move")).thenReturn(new Move(new Position(1,1), new Position(2, 2)));
        when(session.attribute("piece")).thenReturn(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
    }

    /**
     * Setup mock classes to fill dependencies through
     * the test seam
     */
    @Test
    public void test1() {
        Gson gson = new Gson();

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        Player p1 = new Player("1");
        p1.setColor(Player.Color.WHITE);
        Player p2 = new Player("2");
        p2.setColor(Player.Color.RED);
        playerLobby.getGameCenter().newGame(p1,p2);
        assertNotNull(playerLobby.getGameCenter().getGame(p1));
        when(request.queryParams("activeColor")).thenReturn("RED");
        when(session.attribute("player")).thenReturn(p1);
        playerLobby.getGameCenter().setJustEnded(p1,p2,true);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }

    /**
     * Setup mock classes to fill dependencies through
     * the test seam
     */
    @Test
    public void test2() {
        Gson gson = new Gson();

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can submit a turn in the state you are in.");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        Player p1 = new Player("1");
        p1.setColor(Player.Color.WHITE);
        Player p2 = new Player("2");
        p2.setColor(Player.Color.RED);
        rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i, Piece.COLOR.RED));
        }
        playerLobby = new PlayerLobby(new GameCenter());
        Game game = playerLobby.getGameCenter().newGame(p1, p2);
        assertNotNull(playerLobby.getGameCenter().getGame(p1));
        //BoardHandler bh = new BoardHandler(playerLobby.getGameCenter().getGame(p1).getBoard(p1));
        when(session.attribute("player")).thenReturn(p1);
        playerLobby.getGameCenter().getGame(p1).getMap().put("activeColor","WHITE");
        System.out.println(game.getMap());

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }

    /**
     * test the constructor
     */
    @Test
    public void ctor(){
        assertNotNull(CuT.playerLobby);
        assertNotNull(CuT);
    }

    /**
     * test if successful submit of a move
     */
    @Test
    public void messageIsInfo(){

        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not submit a turn in the state you are in.");
        Gson gson = new Gson();

        when(session.attribute("player")).thenReturn(new Player("Player"));
        playerLobby.getGameCenter().newGame(new Player("Player"), new Player("OPP"));
        playerLobby.getGameCenter().getGame(new Player("Player")).getMap().put("activeColor", "RED");
        playerLobby.getGameCenter().getGame(new Player("Player")).getMap().put("board", playerLobby.getGame(new Player("Player")).getBoardView1());
    }
}