package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Unit test for GameData
 * @author Matthew Klein
 */

@Tag("Ui-tier")
public class GameDataTest {

    /**
     * Component under test (CuT)
     */

    private GameData CuT;
    //the map of the vm
    private Map<String, Object> vm;
    //the current user
    private Player currentUser;
    //the view mode of the game
    private String viewMode;
    //the options for specific modes of the games
    private String modeOptionsAsJSON;
    // the red player
    private Player redPlayer;
    //the white player
    private Player whitePlayer;
    //the player color that is active and allowed to move
    private String activeColor;
    //the board
    private BoardView board;

    /**
     * Setup mock classes to fill dependencies through
     * the test seam
     */
    @BeforeEach
    public void testSetup() {
        vm = new HashMap<>();
        currentUser = mock(Player.class);
        viewMode = mock(String.class);
        modeOptionsAsJSON = mock(String.class);
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        activeColor = mock(String.class);
        board = mock(BoardView.class);
        CuT = new GameData();
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor(){
        assertNull(CuT.getVm(), "The Template Engine is Null and should not be");
    }

    /**
     * Test the Post UI for adding the first player to the game
     */
    @Test
    public void first_player() {
        //Arrange scenario
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        //Add a player to a new empty lobby

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine

        // Invoke the test

        //Check if UI received all necessary parameters
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("message", PostSignInRoute.WELCOME_MSG);
        //Can't see own name
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }
}
