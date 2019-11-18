package com.webcheckers.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    //Respective boards view for each player
    private BoardView boardView1;
    private BoardView boardView2;

    //if the game is active
    private Boolean active = true;

    private Map<String, Object> map;

    private ArrayList<Row> rows = new ArrayList<>(7);

    Piece.COLOR currentColor;

    /**
     * Constructor for the game class that sets the board views for each player
     * and sets the players as their respective color
     * @param p1 player 1
     * @param p2 player 2
     */
    public Game (Player p1, Player p2){
        this.player1 = p1;
        p1.setColor(Player.Color.RED);
        this.player2 = p2;
        p1.setInGame(true);
        p2.setColor(Player.Color.WHITE);
        p2.setInGame(true);
        makeBoard();
        boardView1 = new BoardView(rows, p1);
        boardView2 = new BoardView(rows, p2);
        this.map = new HashMap<>();
    }

    public void makeBoard(){
        for (int i = 0; i < 8; i++) {
            //create a new space
            Piece.COLOR color;
            if (i < 4) {
                color = Piece.COLOR.WHITE;
                currentColor = color;
            } else {
                color = Piece.COLOR.RED;
                currentColor = color;
            }
            Row row = new Row(i, color);
            //add that space to the ArrayList
            rows.add(row);
        }
    }

    public void checkGameOver() {
        boolean redWins = true;
        boolean whiteWins = true;
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                //only checking spaces with pieces
                if (rows.get(i).getSpaceAtIndex(j).getPiece() != null) {
                    //check if red pieces on board
                    if (rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE))
                            || rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.KING))) {
                        whiteWins = false;
                    //check if white pieces on board
                    } else if (rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                            || rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING))) {
                        redWins = false;
                    }
                }
            }
        }
        //make modeOptions
        final Map<String, Object> modeOptions = new HashMap<>(2);
        Gson gson = new Gson();
        if (redWins) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", player1.getName() + " has captured all the pieces.");
            //set mode options
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "");
        } else if (whiteWins) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", player2.getName() + " has captured all the pieces.");
            //set mode option
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "");
        } /*else if (!new MoveChecks(this).checkMoves()) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", "No more moves available! It's a tie!");
            //set mode option
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "");
        } */
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public String getActivePlayer(){
        if(this.map.get("activeColor").equals(Piece.COLOR.RED.name())){
            return (String) this.map.get("redPlayer");
        }else{
            return (String) this.map.get("whitePlayer");
        }
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
        return boardView2;
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
    public BoardView getBoard(Player player) {
        if (player.equals(player2)) {
            return boardView2;
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
