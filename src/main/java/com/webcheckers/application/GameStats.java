package com.webcheckers.application;

public class GameStats {

    public String player;
    public int gamesPlayed;
    public int gamesWon;
    public int gamesLost;
    public double winPct;

    public GameStats(String player) {
        this.player = player;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.winPct = 0.0;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGamesPlayed() {
        return String.valueOf(gamesPlayed);
    }

    public void addGamesPlayed(int gamesPlayed) {
        this.gamesPlayed++;
    }

    public String getGamesWon() {
        return String.valueOf(gamesWon);
    }

    public void addGamesWon(int gamesWon) {
        this.gamesWon++;
    }

    public String getGamesLost() {
        return String.valueOf(gamesLost);
    }

    public void addGamesLost(int gamesLost) {
        this.gamesLost++;
    }

    public String getWinPct() {
        if (gamesPlayed >= 1) {
            winPct = (gamesWon / gamesPlayed) * 100;
            return winPct + "%";
        } else {
            return "0%";
        }
    }
}
