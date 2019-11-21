package com.webcheckers.model;

import com.google.gson.Gson;
import com.webcheckers.ui.MoveChecks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    //the vm for this game
    private Map<String, Object> map;
    //the list of rows need to make a board
    private ArrayList<Row> rows = new ArrayList<>(7);
    //the current color of the pieces
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

    /**
     * makes a board
     */
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

    /**
     * check to see if the game is over
     */
    public void checkGameOver() {
        boolean redWins = true;
        boolean whiteWins = true;
        boolean redJumpsAvailable = false;
        boolean redMovesAvailable = false;
        boolean whiteJumpsAvailable = false;
        boolean whiteMovesAvailable = false;
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
                        if (new MoveChecks(this).checkSinglePieceJumps(this.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).size() > 0) {
                            redJumpsAvailable = true;
                        } else if (new MoveChecks(this).checkSinglePieceMoves(this.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).size() > 0) {
                            redMovesAvailable = true;
                        }
                    //check if white pieces on board
                    } else if (rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                            || rows.get(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING))) {
                        redWins = false;
                        if (new MoveChecks(this).checkSinglePieceJumps(this.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).size() > 0) {
                            whiteJumpsAvailable = true;
                        } else if (new MoveChecks(this).checkSinglePieceMoves(this.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).size() > 0) {
                            whiteMovesAvailable = true;
                        }
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
            this.map.put("activeColor", "RED");
        } else if (whiteWins) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", player2.getName() + " has captured all the pieces.");
            //set mode option
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "WHITE");
        } else if (!redJumpsAvailable && !redMovesAvailable) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", player1.getName() + " has no available moves. " + player2.getName() + " is the winner!");
            //set mode option
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "WHITE");
        } else if (!whiteJumpsAvailable && !whiteMovesAvailable) {
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", player2.getName() + " has no available moves. " + player1.getName() + " is the winner!");
            //set mode option
            this.map.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            this.map.put("activeColor", "RED");
        }
    }

    /**
     * gets the player color
     * @param player the player who's color is being found
     * @return the color of the player
     */
    public Player.Color getPlayerColor(Player player) {
        if (player == player1) {
            return player1.getColor();
        } else {
            return player2.getColor();
        }
    }

    /**
     * check that no rows are null
     * @return null if they are
     */
    public Object checkNonNullRows() {
        if (getBoardView1().getRows().size() == 8 && getBoardView1().getRows().get(7) != null) return getBoardView1().getRows().get(7);
        else if (getBoardView1().getRows().size() == 7 && getBoardView1().getRows().get(6) != null) return getBoardView1().getRows().get(6);
        else if (getBoardView1().getRows().size() == 6 && getBoardView1().getRows().get(5) != null) return getBoardView1().getRows().get(5);
        else if (getBoardView1().getRows().size() == 5 && getBoardView1().getRows().get(4) != null) return getBoardView1().getRows().get(4);
        else if (getBoardView1().getRows().size() == 4 && getBoardView1().getRows().get(3) != null) return getBoardView1().getRows().get(3);
        else if (getBoardView1().getRows().size() == 3 && getBoardView1().getRows().get(2) != null) return getBoardView1().getRows().get(2);
        else if (getBoardView1().getRows().size() == 2 && getBoardView1().getRows().get(1) != null) return getBoardView1().getRows().get(1);
        else if (getBoardView1().getRows().size() == 1 && getBoardView1().getRows().get(0) != null) return getBoardView1().getRows().get(0);
        else return null;
    }

    /**
     * Get's the vm map for a game
     * @return the vm map for a game
     */
    public Map<String, Object> getMap() {
        return this.map;
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
     * Only used for testing
     * removes the players
     */
    public void removePlayers(){
        player1 = null;
        player2 = null;
    }

    /**
     * resets player 1
     */
    public void removePlayer1() {
        if (player1 != null)
            player1 = null;
    }

    /**
     * resets player 2
     */
    public void removePlayer2() {
        if (player2 != null)
            player2 = null;
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

    /**
     * check to see if two games are equak
     * @param o the other object being compared
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(player1, game.player1) &&
                Objects.equals(player2, game.player2);
    }

    /**
     * hashcode for this object
     * @return hashcode for this game
     */
    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }
}
