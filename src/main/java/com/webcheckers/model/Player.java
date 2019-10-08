/**
 * Player entity that can hold a unique player name
 *
 * @author Matthew Klein
 */

package com.webcheckers.model;

public class Player {

    private String name;

    public Player(String name){
        this.name = name;
    }

    /**
     * @return The player's unique name
     */
    public String getName(){
        return name;
    }

    /**
     * set a new player's unique name
     * @param name
     */
    public void setName(String name){
        this.name = name;
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
