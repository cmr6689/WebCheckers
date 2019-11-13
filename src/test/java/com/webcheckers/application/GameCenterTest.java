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
        game = CuT.getGame(null);
        // Analyze the results
        // 1) the returned game is not real
        assertNull(game);

        //create players to add to the game
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        //create a new game
        CuT.newGame(test1,test2);

        // Analyze the results
        // 1) the returned game is real
        assertNotNull(CuT.getGame(test1));
        // 2) the game is Active
        assertTrue(CuT.getGame(test1).isActive());

    }



    /**
     * Test the ability to end a Game.
     */
    @Test
    public void test_end_game() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame(null);
        //create players to add to game
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        //start game
        CuT.newGame(test1,test2);
        //end game
        CuT.endGame(test1);

        // Analyze the results
        // 2) the game is not Active
        assertFalse(CuT.getGame(test1).isActive());
    }

    /**
     * Test the ability to make a new Game.
     */
    @Test
    public void test_active_games_list() {
        final GameCenter CuT = new GameCenter();
        // Invoke test

        //create players to add to the game
        test1 = new Player("Test1");
        test2 = new Player("Test2");
        //create the game
        CuT.newGame(test1,test2);

        // Analyze the results
        // 1) the returned game is real
        assertNotNull(CuT.getGame(test1));
        // 2) the game is Active
        assertTrue(CuT.getGame(test1).isActive());
        //3) the game is in the arrayList for active games
        assertTrue(CuT.gameIsActive(CuT.getGame(test1)));

        assertEquals(CuT.getGame(test1), CuT.getGame(test2));
        assertEquals(CuT.getActiveGame(test1), CuT.getActiveGame(test2));
    }

    /**
     * Test the ability to make a new Game.
     */
    @Test
    public void test_dormant_games_list() {
        final GameCenter CuT = new GameCenter();
        // Invoke test
        game = CuT.getGame(null);
        //create players to put into the game
        test1 = new Player("Test1");
        test2 = new Player("Test2");
        //create game
        CuT.newGame(test1,test2);
        //check that the game has started
        assertTrue(CuT.getGame(test1).isActive());
        //end the game
        assertNotNull(CuT.endGame(test1));
        // Analyze the results
        // 1) the returned game is not real
        assertNull(CuT.getActiveGame(test1));
        // 2) the game is not Active
        assertFalse(CuT.getGame(test1).isActive());
        //3) the game is in the dormant games arrayList
    }
}
