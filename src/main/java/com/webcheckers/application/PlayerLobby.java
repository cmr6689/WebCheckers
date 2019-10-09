package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>();

    private boolean invalidName;

    public PlayerLobby() {
        this.invalidName = false;
    }

    public boolean addPlayer(Player newPlayer) {
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
