package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    public ArrayList<Player> players = new ArrayList<>();

    public PlayerLobby() {}

    public boolean addPlayer(Player newPlayer) {
        for (Player player : players) {
            if (player.getName().toLowerCase().equals(newPlayer.getName().toLowerCase())) {
                return false;
            }
        }
        players.add(newPlayer);
        return true;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
