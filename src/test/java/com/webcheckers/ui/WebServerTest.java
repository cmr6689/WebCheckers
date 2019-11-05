package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WebServerTest {

    private WebServer CuT;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameData gameData = new GameData();

    @Test
    public void testCTo(){
        templateEngine = mock(TemplateEngine.class);
        Gson gson = new Gson();
        CuT = new WebServer(templateEngine, gson);
        assertNotNull(CuT);
    }

    @Test
    public void testTemplates(){
        templateEngine = mock(TemplateEngine.class);
        TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        Gson gson = new Gson();
        CuT = new WebServer(templateEngine, gson);
        CuT.initialize();

        GetHomeRoute getHomeRoute = new GetHomeRoute(templateEngine, new PlayerLobby(new GameCenter()));

//        templateEngineTester.assertViewModelExists();

    }

}