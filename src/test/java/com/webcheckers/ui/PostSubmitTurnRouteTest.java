package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostSubmitTurnRouteTest {

    private Gson gson;
    PlayerLobby playerLobby;

    private Session session;
    private Request request;
    private Response response;

    Player pl1 = new Player("1");
    Player pl2 = new Player("2");

    private PostSubmitTurnRoute CuT;

    @BeforeEach
    public void testSetup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        playerLobby = new PlayerLobby(new GameCenter());
        CuT = new PostSubmitTurnRoute(playerLobby);

        playerLobby.addPlayer(pl1);
        playerLobby.addPlayer(pl2);
        playerLobby.getGameCenter().newGame(pl1, pl2);

        final Session httpSession = request.session();
        when(session.attribute("player")).thenReturn(pl1);
        httpSession.attribute("player", pl1);

    }

    @Test
    public void ctor(){
        assertNotNull(CuT.playerLobby);
        assertNotNull(CuT);
    }

//    @Test
//    public void changeActiveToRed(){
//        CuT.handle(request, response);
//        assertEquals("RED", null);
//    }
//
//    @Test
//    public void changeActiveToWhite(){
//        CuT.handle(request, response);
//        assertEquals("WHITE", null);
//    }

    @Test
    public void messageIsInfo(){

        ResponseMessage message = new ResponseMessage();
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not submit a turn in the state you are in.");
        Gson gson = new Gson();

        assertEquals(gson.toJson(message), CuT.handle(request, response));
    }
}