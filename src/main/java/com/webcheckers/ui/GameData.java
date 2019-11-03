package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameData class handles the data needed for the game's vm map
 *
 * @author Team-E
 */
public class GameData {
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
     * Constructor for the game data class that sets all the global variables
     * to null or a new map
     */
    public GameData(){
        this.vm = new HashMap<>();
        this.currentUser = null;
        this.viewMode = null;
        this.modeOptionsAsJSON = null;
        this.redPlayer = null;
        this.whitePlayer = null;
        this.activeColor = null;
        this.board = null;
    }

    /**
     * Get the vm map for the game
     * @return the vm map
     */
    public Map<String, Object> getVm() {
        return vm;
    }

    /**
     * Set the vm map for the game
     * @param vm the vm map
     */
    public void setVm(Map<String, Object> vm) {
        this.vm = vm;
    }

    /**
     * Set the current user of the game who is allowed to make moves
     * @param currentUser a player
     */
    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Set the view mode for the game, (PLAY)
     * @param viewMode the string with the view mode
     */
    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    /**
     * Set the mode options which will be converted to a JSON string
     * @param modeOptionsAsJSON string of options
     */
    public void setModeOptionsAsJSON(String modeOptionsAsJSON) {
        this.modeOptionsAsJSON = modeOptionsAsJSON;
    }

    /**
     * Set the red player for the game
     * @param redPlayer the red player
     */
    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    /**
     * Set the white player for the game
     * @param whitePlayer the white player
     */
    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    /**
     * Set the active color of the game
     * @param activeColor the string of the color of the player that can now make moves
     */
    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }


    /**
     * Set the game to be used for its functionality
     * @param board the game
     */
    public void setBoard(BoardView board) {
        this.board = board;
    }

    /**
     * Given that all the vm values have been filled in this method will
     * set the vm or provide updates to specific attributes in the vm.
     */
    public void dataSetup(){
        //variables for game
//                vm.put("currentUser", myPlayer.getName());
//                vm.put("viewMode", "PLAY");
//                vm.put("modeOptionsAsJSON!", null);
//                vm.put("redPlayer", myPlayer.getName());
//                vm.put("whitePlayer", opponent.getName());
//                vm.put("activeColor", "RED");
//                vm.put("board", game.getGame(myPlayer));
        vm.put("currentUser", currentUser.getName());
        vm.put("viewMode", viewMode);
        vm.put("modeOptionsAsJSON!", modeOptionsAsJSON);
        vm.put("redPlayer", redPlayer.getName());
        vm.put("whitePlayer", whitePlayer.getName());
        vm.put("activeColor", activeColor);
        vm.put("board", board);
    }
}
