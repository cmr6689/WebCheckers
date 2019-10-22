package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

public class GameCenter {

    //array list to hold all the instances of the games that are active
    private ArrayList<Game> activeGames;
    //array list to hold all the instances of the games that aren't active
    private ArrayList<Game> dormantGames;
    //individual game with the players
    private Game game;
    //variable to hold temporary games to check names
    private Game temp;
    //player names combined to make the game name
    private String gameName;

    public GameCenter(){
        activeGames = new ArrayList<>();
        dormantGames = new ArrayList<>();
    }

    /**
     * Create a new game and add it to the list of games
     * @param p1: the first player
     * @param p2: the second player
     * @return: the game created
     */
    public Game newGame(Player p1, Player p2){
        //gameName = p1.getName()+p2.getName();
        game = new Game(p1,p2);
        game.setIsActive(true);
        activeGames.add(game);
        return game;
    }

    /**
     * Iterates through the list of active games and checks the players in each game
     * if the names are the same as the names who's game resigned remove the game
     * adds the removed game to the dormant games ArrayList
     * @param p1: player 1
     * @param p2: player 2
     * @return: the game if it was there, null if it wasn't
     */
    public Game endGame(Player p1,Player p2){
        //iterate through games
        for(int i = 0; i < activeGames.size(); i++){
            //get the game at the index
            temp = activeGames.get(i);
            //check to see if the names match
            if(p1.getName().equals(temp.getPlayer1Name()) && p2.getName().equals(temp.getPlayer2Name())){
                //if they do remove the game from the active list and return it and set it to be dormant
                activeGames.remove(i);
                temp.setIsActive(false);
                dormantGames.add(temp);
                return temp;
            }
        }
        //if the game wasn't in the list return null
        return null;
    }

    /**
     * returns the current game
     * @return
     */
    public Game getGame() {
        return game;
    }
}
