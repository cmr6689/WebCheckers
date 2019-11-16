package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The PlayerLobby class keeps track of all the players logged onto the server
 * as well as a map of the vm of each of the games the players are playing.
 *
 * @author Team-E
 */
public class PlayerLobby {
    //arraylist of players
    private ArrayList<Player> players = new ArrayList<>();
    //boolean to tell if name is valid
    private boolean invalidName;
    //game handler
    private GameCenter gameCenter;

    /**
     * PlayerLobby constructor the sets the invalid name boolean and the gamecenter class
     * @param gameCenter gamecenter that is passed in from the server
     */
    public PlayerLobby(GameCenter gameCenter) {
        this.invalidName = false;
        this.gameCenter = gameCenter;
        Player AI = new Player("AI");
        addPlayer(AI);
    }

    /**
     * Method to add a player to the lobby that has signed into the server
     * @param newPlayer a player who has just signed in
     * @return true if the player was successfully added and signed in
     */
    public boolean addPlayer(Player newPlayer) {
        String REGEX = "\\W";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(newPlayer.getName());
        if(matcher.lookingAt()){
            setInvalidName(true);
            return false;
        }else {
            REGEX = "^\\s*$";
            pattern = Pattern.compile(REGEX);
            matcher = pattern.matcher(newPlayer.getName());
            if (matcher.lookingAt()) {
                setInvalidName(true);
                return false;
            }
        }

        for (Player player : players) {
            if (player.getName().toLowerCase().equals(newPlayer.getName().toLowerCase())) {
                setInvalidName(true);
                return false;
            }
        }
        players.add(newPlayer);
        return true;
    }

    /**
     * Invalid name boolean setter
     * @param invalidName boolean
     */
    public void setInvalidName(boolean invalidName){
        this.invalidName = invalidName;
    }

    /**
     * Invalid name getter
     * @return true or false
     */
    public boolean getInvalidName(){
        return invalidName;
    }

    /**
     * Array list of players getter
     * @return the list of players in the lobby
     */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /**
     * Method to get all players that are not currently in a game
     * @return a list of players
     */
    public ArrayList<Player> getAvaPlayers() {
        ArrayList<Player> tmp = new ArrayList<>();

        for(Player player : players){
            if(!player.getInGame())
                tmp.add(player);
        }

        return tmp;
    }

    /**
     * Game getter
     * @param p1 a player that is in a game
     * @return the game the player is in
     */
    public Game getGame(Player p1) {
        return gameCenter.getGame(p1);
    }

    /**
     * GameCenter getter
     * @return the game center
     */
    public GameCenter getGameCenter() {
        return gameCenter;
    }

}
