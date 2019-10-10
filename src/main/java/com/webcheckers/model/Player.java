/**
 * Player entity that can hold a unique player name
 *
 * @author Matthew Klein
 */

package com.webcheckers.model;

public class Player {

    private enum Color{
        RED,WHITE;
    }

    private String name;
    private boolean inGame = false;
    private Color playerColor;

    public Player(String name){
        this.name = name;
        this.playerColor = null;
    }

    /**
     * @return The player's unique name
     */
    public String getName(){
        return name;
    }

    /**
     * @return The player's color
     */
    public Color getColor(){
        return playerColor;
    }

    /**
     * @return If the player is in a game
     */
    public boolean getInGame(){
        return inGame;
    }

    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }

    /**
     * set a new player's unique name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * set a player's color once a game is started
     * @param color
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

}
