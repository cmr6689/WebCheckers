package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCenter {


    private HashMap<Player, Game> activeGames;

    private HashMap<Player, Game> dormantGames;


    public GameCenter(){
        activeGames = new HashMap<>();
        dormantGames = new HashMap<>();
    }

    /**
     * Create a new game and add it to the list of games
     * @param p1: the first player
     * @param p2: the second player
     * @return: the game created
     */
    public Game newGame(Player p1, Player p2){
        //gameName = p1.getName()+p2.getName();
        activeGames.put(p1, new Game(p1, p2));
        activeGames.get(p1).setIsActive(true);
        return activeGames.get(p1);
    }

    /**
     * Iterates through the list of active games and checks the players in each game
     * if the names are the same as the names who's game resigned remove the game
     * adds the removed game to the dormant games ArrayList
     * @param p1: player 1
     * @return: the game if it was there, null if it wasn't
     */
    public Game endGame(Player p1){
        //iterate through
        if(activeGames.containsValue(p1)) {
            activeGames.get(p1).setIsActive(false);
            return activeGames.get(p1);
        }
        else
            return null;
    }

    /**
     * returns the active game with the given player in it
     * @return
     */
    public Game getGame(Player player) {
        if(activeGames.containsKey(player))
            return activeGames.get(player);
        for(Game game : activeGames.values()){
            if(game.getPlayer2().equals(player))
                return game;
        }
        return null;
    }

    /**
     * checks if this game is in the active games arraylist
     * @return if the game is active
     */
    public Boolean gameIsActive(Game name){
        return activeGames.containsValue(name);
    }

    /**
     * checks if this game is in the dormant games arraylist
     * @return if the game is the dormant games arrayList
     */
    public Boolean gameIsDormant(Game name) {
        return dormantGames.containsValue(name);
    }
}
