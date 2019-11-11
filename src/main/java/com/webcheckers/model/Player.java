/**
 * Player entity that can hold a unique player name
 *
 * @author Matthew Klein
 */

package com.webcheckers.model;

/**
 * The player class handles the color of the player, the name, and
 * whether they are in a game or not.
 *
 * @author Team-E
 */
public class Player {

    /**
     * The players color can either be red or white
     */
    public enum Color{
        RED,WHITE;
    }
    //the name of the player
    private String name;
    //whether they are in a game or not
    private boolean inGame = false;
    //the color of the player
    private Color playerColor;

    /**
     * The constructor for the player class that sets the name and the color to null since they are
     * not in a game yet when they sign in
     * @param name the name of the player
     */
    public Player(String name){
        this.name = name;
        this.playerColor = null;
    }

    /**
     * A getter for the name of the player
     * @return The player's unique name
     */
    public String getName(){
        return name;
    }

    /**
     * A getter for the players color
     * @return The player's color
     */
    public Color getColor(){
        return playerColor;
    }

    /**
     * Getter for the boolean value if a player is in a game or not
     * @return If the player is in a game
     */
    public boolean getInGame(){
        return inGame;
    }

    /**
     * Setter for the in game boolean value
     * @param inGame boolean in game
     */
    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }

    /**
     * set a new player's unique name
     * @param name the name of the player
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * set a player's color once a game is started
     * @param color the color of the player
     */
    public void setColor(Color color){
        this.playerColor = color;
    }

    /**
     * Compares other instances of player's with the same names and distinguishes
     * them as two different objects
     * @param obj
     * @return The player's unique name if it really is a separate instance
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) return false;
        final Player that = (Player) obj;
        return this.name.equals(that.name);
    }

    /**
     * Allocates a unique spot in hashcode for the player's unique name
     * @return The player name hashcode location
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * @return the name and color of the player
     */
    @Override
    public String toString(){
        return "The name of this player is " + name + " and their color is " + playerColor;
    }
}
