package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
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
    private GameData gameData;
    PlayerLobby playerLobby;

    private Session session;
    private Request request;
    private Response response;

    private PostSubmitTurnRoute CuT;

    @BeforeEach
    public void testSetup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        playerLobby = new PlayerLobby(new GameCenter());
        gameData = new GameData();
        CuT = new PostSubmitTurnRoute(gameData, playerLobby);
    }

    @Test
    public void ctor(){
        assertNotNull(CuT.gameData);
        assertNotNull(CuT.playerLobby);
        assertNotNull(CuT);
    }

    @Test
    void handle() {

    }
}