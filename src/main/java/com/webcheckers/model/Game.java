package com.webcheckers.model;

/**
 * The game class responsible for controlling the board views and the players
 * that are playing the game.
 *
 * @author Team-E
 */
public class Game {

    //Player 1 of the game
    private Player player1;

    //Player 2 of the game
    private Player player2;

    //Respective boards veiw for each player
    private BoardView boardView1;
    //private BoardView boardView2;

    //if the game is active
    private Boolean active = true;

    /**
     * Constructor for the game class that sets the board views for each player
     * and sets the players as their respective color
     * @param p1 player 1
     * @param p2 player 2
     */
    public Game (Player p1, Player p2){
        //boardView = new BoardView();
        boardView1 = new BoardView(1);
        //boardView2 = new BoardView(2);
        this.player1 = p1;
        this.player2 = p2;
    }

    /**
     * Getter for the red players board view
     * @return board view 1
     */
    public BoardView getBoardView1() {
        return boardView1;
    }

    /**
     * Getter for the white players board view
     * @return board view 2
     */
    public BoardView getBoardView2() {
        return boardView1;
    }

    /**
     * Set the player that is player one and the red player
     * @param player1 the player to be player 1
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Set the player that is player one and the white player
     * @param player2 the player to be player 2
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Get the red player
     * @return player one
     */
    public Player getPlayer1(){
        return this.player1;
    }

    /**
     * Get the white player
     * @return player two
     */
    public Player getPlayer2(){
        return this.player2;
    }

    /**
     * Get the red players name
     * @return the name of player one
     */
    public String getPlayer1Name(){
        return this.player1.getName();
    }

    /**
     * Get the white players name
     * @return the name of player two
     */
    public String getPlayer2Name(){
        return this.player2.getName();
    }

    /**
     * Getter to see if the game is active
     * @return true or false
     */
    public Boolean isActive(){
        return this.active;
    }

    /**
     * Set the game to active or inactive
     * @param bool active or not
     */
    public void setIsActive(Boolean bool){
        active = bool;
    }

    /**
     * Set the player to be player one if player one does not already exist
     * @param p player
     */
    public void setPlayer(Player p){
        if(player1 != null)
            player2 = p;
        else
            player1 = p;
    }

    /**
     * Get the board view based on the player
     * @param player player one or player two
     * @return board view 1 if player 1 or board view 2 if player 2
     */
    public BoardView getGame(Player player) {
        if (player.equals(player2)) {
            return boardView1;
        }
        return boardView1;
    }

    /**
     * String message of who is playing against who
     * @return string of player names competing
     */
    @Override
    public String toString() {
        return player1.getName() + " vs " + player2.getName();
    }
}
