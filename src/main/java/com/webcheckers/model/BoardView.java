package com.webcheckers.model;

import com.webcheckers.model.Piece.COLOR;

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
    private ArrayList<Row> otherBoard = new ArrayList<>(7);
    //the current color
    int i;
    int numMovs;
    COLOR currentColor;
    ArrayList<Position> removedPieces = new ArrayList<>();
    ArrayList<Move> movesThisTurn = new ArrayList<>();
    Position originalPos;
    Position finalPos;

    /**
     * Constructor for the board view that creates the orientation
     * of the board based on the color that the player is
     *
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
     *
     * @return the iterator of the spaces
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    /**
     * Class needed in order for the board to have a reversed
     * version of itself
     *
     * @return the reversed version of the iterator of the spaces
     */
    public Iterator<Row> reverseIterator() {
        for(int i = 7; i >=0; i--){
            otherBoard.add(rows.get(i));
        }
        return otherBoard.iterator();
    }

    /**
     * getter for the array list of rows created
     *
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
     * getter for the array list of otherBoard created
     *
     * @return the list of rows in reverse
     */
    public ArrayList<Row> getOtherBoard(){
        return otherBoard;
    }

    /**
     * get the number of movs this turn
     * @return
     */
    public int getNumMovs(){
        return this.numMovs;
    }

    /**
     * increase the number of movs
     * @return
     */
    public void increaseNumMovs(){
        this.numMovs++;
    }

    /**
     *
     */
    public void decreaseNumMoves(){
        this.numMovs--;
    }

    /**
     * set the number of movs this turn to 0
     */
    public void resetMovs(){
        this.numMovs = 0;
    }

    /**
     * Method to retrieve a specific row from the list of rows
     *
     * @param index the row number
     * @return the row
     */
    public Row getRowAtIndex(int index) {
        return this.rows.get(index);
    }

    public void clearRemovedPieces(){
        removedPieces.clear();
    }

    public ArrayList<Position> getRemovedPieces(){
        return removedPieces;
    }

    public void setRemovedPiece(Position p){
        removedPieces.add(p);
    }

    public void clearMovesThisTurn(){
        movesThisTurn.clear();
    }

    public ArrayList<Move> getMovesThisTurn(){
        return movesThisTurn;
    }

    public void setMoveThisTurn(Move m){
        movesThisTurn.add(m);
    }

    public void decrementMovesThisTurn(){
        movesThisTurn.remove(movesThisTurn.size()-1);
    }

    public void setOriginalPos(Position p){
        originalPos = p;
    }

    public void setFinalPos(Position p){
        finalPos = p;
    }

    public Position getOriginalPos(){
        return this.originalPos;
    }

    public Position getFinalPos(){
        return this.finalPos;
    }

    public void resetPositions(){
        this.originalPos = null;
        this.finalPos = null;
    }

    public void backupPiece(){
        //remove the last element
        removedPieces.remove(removedPieces.size()-1);
    }
}
