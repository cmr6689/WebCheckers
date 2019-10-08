package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    public ArrayList<Player> players;

    public PlayerLobby(ArrayList<Player> players) {
        this.players = players;
    }

    public int addPlayer(Player newPlayer) {
        for (Player player : players) {
            if (player.getName().toLowerCase().equals(newPlayer.getName().toLowerCase())) {
                return 0;
            }
        }
        players.add(newPlayer);
        return 1;
    }
}
