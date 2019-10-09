package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>();

    private boolean invalidName;

    public PlayerLobby() {
        this.invalidName = false;
    }

    public boolean addPlayer(Player newPlayer) {
        String REGEX = "^[a-zA-Z0-9 ]+$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(newPlayer.getName());
        if(!matcher.lookingAt()){
            return false;
        }
        for (Player player : players) {
            if (player.getName().toLowerCase().equals(newPlayer.getName().toLowerCase())) {
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
}
