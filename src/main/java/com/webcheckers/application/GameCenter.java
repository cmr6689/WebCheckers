package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The GameCenter class handles multiple games on the server at once.
 *
 * @author Team-E
 */
public class GameCenter {


    //Hashmap of the active games
    private HashMap<Player, Game> activeGames;

    /**
     * GameCenter constructor which sets the hashmaps
     */
    public GameCenter(){
        activeGames = new HashMap<>();
    }

    /**
     * Create a new game and add it to the list of games
     * @param p1: the first player
     * @param p2: the second player
     * @return: the game created
     */
    public Game newGame(Player p1, Player p2){
        //gameName = p1.getName()+p2.getName();
        p1.setInGame(true);
        p2.setInGame(true);
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
    public boolean endGame(Player p1){
        //iterate through
        if(activeGames.containsKey(p1)) {
            activeGames.get(p1).setIsActive(false);
            activeGames.get(p1).getPlayer2().setInGame(false);
            p1.setInGame(false);
            activeGames.remove(p1);
            return true;
        }
        else
            return false;
    }

    /**
     * returns the active game with the given player in it
     * @param player a player within the game
     * @return the game the player is in if it exists
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
     * return the active game given a player
     * @param player a player within the game
     * @return the game the player is in if it exists
     */
    public Game getActiveGame(Player player){
        return getGame(player);
    }

    /**
     * checks if this game is in the active games arraylist
     * @param name a game
     * @return if the game is active
     */
    public Boolean gameIsActive(Game name){
        return activeGames.containsValue(name);
    }

    public void removePlayer(Player player){
        this.activeGames.remove(player);
    }
}
