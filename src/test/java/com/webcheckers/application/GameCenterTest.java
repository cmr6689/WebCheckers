package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link GameCenter} component.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Evan Price</a>
 */
@Tag("Aplication-tier")
public class GameCenterTest {

    private Player test1;
    private Player test2;
    private Game game;
    /**
     * Test the ability to make a new Game.
     */
    @Test
    public void test_make_game() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame();
        // Analyze the results
        // 1) the returned game is real
        assertNull(game);

        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        CuT.newGame(test1,test2);

        // Analyze the results
        // 1) the returned game is not real
        assertNotNull(CuT.getGame());
        // 2) the game is not Active
        assertTrue(CuT.getGame().isActive());
    }

    /**
     * Test the ability to end a Game.
     */
    @Test
    public void test_end_game() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame();
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        CuT.newGame(test1,test2);
        CuT.endGame(test1,test2);

        // Analyze the results
        // 1) the returned game is not real
        assertNull(game);
        // 2) the game is not Active
        assertFalse(CuT.getGame().isActive());
    }

    /**
     * Test the ability to make a new Game.
     */
    @Test
    public void test_active_games_list() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame();
        test1 = new Player("Test1");
        test2 = new Player("Test2");
        CuT.newGame(test1,test2);

        // Analyze the results
        // 1) the returned game is real
        assertNotNull(CuT.getGame());
        // 2) the game is Active
        assertTrue(CuT.getGame().isActive());
        //3)
        assertTrue(CuT.gameIsActive(CuT.getGame()));
    }

    /**
     * Test the ability to make a new Game.
     */
    @Test
    public void test_dormant_games_list() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame();
        test1 = new Player("Test1");
        test2 = new Player("Test2");
        CuT.newGame(test1,test2);
        assertTrue(CuT.getGame().isActive());
        CuT.endGame(test1,test2);
        // Analyze the results
        // 1) the returned game is real
        assertNull(game);
        // 2) the game is Active
        assertFalse(CuT.getGame().isActive());
        //3)
        assertTrue(CuT.gameIsDormant(CuT.getGame()));
    }
}
