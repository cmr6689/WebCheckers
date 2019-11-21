package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The BoardView class controls how the board is viewed by each player
 * It sends different board views for player one and for player two
 *
 * @author Team-E
 */
public class BoardView implements Iterable {
    //create a new ArrayList of Rows
    private ArrayList<Row> rows;
    //the number of moves a turn
    int numMovs;
    //A list of pieces jumped to be removed
    ArrayList<Position> removedPieces = new ArrayList<>();
    //a list of all moves made this turn
    ArrayList<Move> movesThisTurn = new ArrayList<>();
    //the original starting position this turn
    Position originalPos;
    //the final ending position this turn
    Position finalPos;
    //if the last move was a jump
    Boolean lastWasJump = false;

    /**
     * Constructor for the board view that creates the orientation
     * of the board based on the color that the player is
     */
    public BoardView(ArrayList<Row> rows, Player player) {
        if(player.getColor().equals(Player.Color.RED)) {
           this.rows = rows;
        }else{
            ArrayList<Row> otherBoard = new ArrayList<>(7);
            ArrayList<Row> nonFlipped = new ArrayList<>(7);
            Row reverse;
            for(int i = 0; i < 8; i++){
                ArrayList<Space> space = new ArrayList<>();
                for(int j = 7; j >=0; j--) {
                    space.add(rows.get(i).getSpaceAtIndex(j));
                }
                reverse = new Row(i, space);
                nonFlipped.add(reverse);
            }
            for(int i = 7; i >= 0; i--){
                otherBoard.add(nonFlipped.get(i));
            }
            this.rows = otherBoard;
        }
    }

    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    /**
     * getter for the array list of rows created
     * @return the list of rows
     */
    public ArrayList<Row> getRows() {
        return rows;
    }

    /**
     * Sets the rows for test cases
     * @param rows the new rows
     */
    public void setRows(ArrayList<Row> rows){this.rows = rows;}

    /**
     * get the number of movs this turn
     * @return the number of moves this turn
     */
    public int getNumMovs(){
        return this.numMovs;
    }

    /**
     * increment the number of moves this turn
     */
    public void increaseNumMovs(){
        this.numMovs++;
    }

    /**
     * set the number of moves this turn to 0
     */
    public void resetMovs(){
        this.numMovs = 0;
    }

    /**
     * Method to retrieve a specific row from the list of rows
     * @param index the row number
     * @return the row
     */
    public Row getRowAtIndex(int index) {
        return this.rows.get(index);
    }

    /**
     * Clears the arrayList of pieces to be removed
     */
    public void clearRemovedPieces(){
        removedPieces.clear();
    }

    /**
     * Gets the arrayList of pieces to be removed
     * @return the arrayList of positions of the pieces to be removed
     */
    public ArrayList<Position> getRemovedPieces(){
        return removedPieces;
    }

    /**
     * add a piece to be removed
     * @param p the position of the piece being removed
     */
    public void setRemovedPiece(Position p){
        removedPieces.add(p);
    }

    /**
     * clears the moves this turn
     */
    public void clearMovesThisTurn(){
        movesThisTurn.clear();
    }

    /**
     * Gets the list of moves this turn
     * @return an array list of moves this turn
     */
    public ArrayList<Move> getMovesThisTurn(){
        return movesThisTurn;
    }

    /**
     * add a move to the list of moves this turn
     * @param m the move being added to the arrayList
     */
    public void setMoveThisTurn(Move m){
        movesThisTurn.add(m);
    }

    /**
     * sets the original position of the piece
     * @param p the original position of the piece
     */
    public void setOriginalPos(Position p){
        originalPos = p;
    }

    /**
     * sets the final position of the piece
     * @param p the final position of the piece
     */
    public void setFinalPos(Position p){
        finalPos = p;
    }

    /**
     * gets the original position of the piece
     * @return the original position
     */
    public Position getOriginalPos(){
        return this.originalPos;
    }

    /**
     * gets the final position of the piece
     * @return the final position of the piece
     */
    public Position getFinalPos(){
        return this.finalPos;
    }

    /**
     * resets the original and final positions to null after each turn is submitted
     */
    public void resetPositions(){
        this.originalPos = null;
        this.finalPos = null;
    }

    /**
     * changes the boolean for if the last move was a jump or not
     * @param b true of the last move was a jump, false otherwise
     */
    public void setLastWasJump(Boolean b){
        this.lastWasJump = b;
    }

    /**
     * get if the last move was a jump
     * @return true if the last was a jump and false if it wasn't
     */
    public Boolean getLastWasJump() {
        return lastWasJump;
    }
}
