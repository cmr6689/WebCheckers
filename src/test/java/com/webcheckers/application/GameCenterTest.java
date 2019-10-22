package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link GameCenter} component.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Evan Price</a>
 */
@Tag("Aplication-tier")
public class GameCenterTest {
    /**
     * Test the ability to make a new Game.
     */
    public void test_make_game() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        final Game game = CuT.getGame();

        // Analyze the results
        // 1) the returned game is real
        assertNotNull(game);
        // 2) the game is Active
        assertTrue(game.isActive());
    }

    /**
     * Test the ability to end a Game.
     */
    public void test_end_game() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        final Game game = CuT.getGame();
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        game.setPlayer1(test1);
        game.setPlayer2(test2);
        CuT.endGame(test1,test2);

        // Analyze the results
        // 1) the returned game is not real
        assertNull(game);
        // 2) the game is not Active
        assertFalse(game.isActive());
    }

    /**
     * Test the ability to make a new Game.
     */
    public void test_active_games_list() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        final Game game = CuT.getGame();

        // Analyze the results
        // 1) the returned game is real
        assertNotNull(game);
        // 2) the game is Active
        assertTrue(game.isActive());
    }
}
