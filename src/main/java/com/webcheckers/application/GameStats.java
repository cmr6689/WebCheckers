package com.webcheckers.application;

/**
 * This class is used to track the statistics of games for each player
 *
 * @author Cameron Riu
 */
public class GameStats {

    //player name
    public String player;
    //number of games played
    public int gamesPlayed;
    //number of games won
    public int gamesWon;
    //number of games lost
    public int gamesLost;
    //win percentage
    public double winPct;

    /**
     * Create a new GameStats class with 0 for all stats
     * @param player - player name
     */
    public GameStats(String player) {
        this.player = player;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.winPct = 0.0;
    }

    /**
     * Get the player name
     * @return player name
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Get the num of games played
     * @return - string value of games played
     */
    public String getGamesPlayed() {
        return String.valueOf(gamesPlayed);
    }

    /**
     * Add to the games played
     * @param gamesPlayed - num of games played
     */
    public void addGamesPlayed(int gamesPlayed) {
        this.gamesPlayed++;
    }

    /**
     * Get the string value of the games won
     * @return - games won
     */
    public String getGamesWon() {
        return String.valueOf(gamesWon);
    }

    /**
     * Add to the games won
     * @param gamesWon - num of games won
     */
    public void addGamesWon(int gamesWon) {
        this.gamesWon++;
    }

    /**
     * Get the games lost value
     * @return - games lost
     */
    public String getGamesLost() {
        return String.valueOf(gamesLost);
    }

    /**
     * Add to the games lost
     * @param gamesLost - num of games lost
     */
    public void addGamesLost(int gamesLost) {
        this.gamesLost++;
    }

    /**
     * Return win percentage
     * @return - win percentage
     */
    public String getWinPct() {
        if (gamesPlayed >= 1) {
            winPct = (gamesWon * 100) / gamesPlayed;
            return winPct + "%";
        } else {
            return "0%";
        }
    }
}
