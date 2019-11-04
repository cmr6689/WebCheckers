package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
        viewMode = null;
        modeOptionsAsJSON = null;
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        activeColor = null;
        board = mock(BoardView.class);
        CuT = new GameData();
    }

    /**
     * Ensure that the template engine isn't null
     */
    @Test
    public void ctor(){
        assertNotNull(CuT.getVm(), "The Map is Null and should not be");
    }

    /**
     * Test the data setup process for playing a simple game
     */
    @Test
    public void data_play_setup() {
        //setup
        currentUser = new Player("Name");
        vm.put("currentUser", currentUser.getName());
        viewMode = "PLAY";
        vm.put("viewMode", viewMode);
        vm.put("modeOptionsAsJSON!", modeOptionsAsJSON);
        redPlayer = new Player("Red");
        vm.put("redPlayer", redPlayer.getName());
        whitePlayer = new Player("White");
        vm.put("whitePlayer", whitePlayer.getName());
        activeColor = "RED";
        vm.put("activeColor", activeColor);
        vm.put("board", board);

        //test each placement
        assertEquals(vm.get("currentUser"), "Name");
        assertEquals(vm.get("viewMode"), "PLAY");
        assertNull(vm.get("modeOptionsAsJSON!"));
        assertEquals(vm.get("redPlayer"), "Red");
        assertEquals(vm.get("whitePlayer"), "White");
        assertEquals(vm.get("activeColor"), "RED");
        assertEquals(vm.get("board"), board);

    }
}
