package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameData {

    private Map<String, Object> vm;
    private Player currentUser;
    private String viewMode;
    private String modeOptionsAsJSON;
    private Player redPlayer;
    private Player whitePlayer;
    private String activeColor;
    private Game board;

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

    public Map<String, Object> getVm() {
        return vm;
    }

    public void setVm(Map<String, Object> vm) {
        this.vm = vm;
    }

    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public void setModeOptionsAsJSON(String modeOptionsAsJSON) {
        this.modeOptionsAsJSON = modeOptionsAsJSON;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }

    public void setBoard(Game board) {
        this.board = board;
    }

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
        vm.put("board", board.getGame(currentUser));
    }
}
