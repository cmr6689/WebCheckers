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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addGamesPlayed(int gamesPlayed) {
        this.gamesPlayed++;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void addGamesWon(int gamesWon) {
        this.gamesWon++;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void addGamesLost(int gamesLost) {
        this.gamesLost++;
    }

    public double getWinPct() {
        winPct = (gamesWon/gamesLost)*100;
        return winPct;
    }
}
