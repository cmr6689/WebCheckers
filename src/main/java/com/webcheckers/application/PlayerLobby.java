package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>();

    private boolean invalidName;

    private Game game;
    private GameCenter gameCenter;
    private Map<String, Object> vm;

    public PlayerLobby(GameCenter gameCenter) {
        this.invalidName = false;
        this.gameCenter = gameCenter;
    }

    public boolean addPlayer(Player newPlayer) {
        String REGEX = "\\W";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(newPlayer.getName());
        if(matcher.lookingAt()){
            setInvalidName(true);
            return false;
        }
        for (Player player : players) {
            if (player.getName().toLowerCase().equals(newPlayer.getName().toLowerCase())) {
                setInvalidName(true);
                return false;
            }
        }
        players.add(newPlayer);
        return true;
    }

    public void setInvalidName(boolean invalidName){
        this.invalidName = invalidName;
    }

    public boolean getInvalidName(){
        return invalidName;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame(Player p1) {
        return gameCenter.getGame(p1);
    }

    public GameCenter getGameCenter() {
        return gameCenter;
    }

    public void setMap(Map<String, Object> map) {
        this.vm = map;
    }

    public Map<String, Object> getMap() {
        return this.vm;
    }

}
