package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("UI-tier")
class AIHandlerTest {

    private ArrayList<Row> rows;
    private AIHandler CuT;
    private Game game;
    private PlayerLobby playerLobby;
    private BoardView board;
    private MoveChecks moveChecks;

    @BeforeEach
    public void testSetup(){
        rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i, Piece.COLOR.RED));
        }
        Player player = new Player("Player");
        playerLobby = new PlayerLobby(new GameCenter());
        game = playerLobby.getGameCenter().newGame(new Player("Player"), new Player("Opp"));
        board = game.getBoardView1();
        moveChecks = new MoveChecks(game);
        CuT = new AIHandler(playerLobby, player);
    }

    @Test
    public void ctor () {
        assertNotNull(CuT);
    }


    @Test
    public void game_over(){
        Player player = new Player ("Player");
        Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        playerLobby.getGame(player).getMap().put("modeOptionsAsJSON", modeOptions);

        assertTrue(playerLobby.getGameCenter().gameIsActive(game));
    }

    @Test
    public void no_jump_chains(){
        ArrayList<Move> jumpChain = new ArrayList<>();
        CuT.setJumpChain(jumpChain);

        assertFalse(CuT.getJumps());
        assertFalse(CuT.getDone());
    }

}