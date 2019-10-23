package com.webcheckers.model;
import com.webcheckers.model.Piece.COLOR;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable{
    //create a new ArrayList of Rows
    private ArrayList<Row> rows = new ArrayList<Row>();
    COLOR currentColor;
    int i;

    public BoardView(int player){
        if(player == 1) {
            for (i = 0; i < 8; i++) {
                //create a new space
                COLOR color;
                if (i < 4) {
                    color = COLOR.WHITE;
                    currentColor = color;
                } else {
                    color = COLOR.RED;
                    currentColor = color;
                }
                Row row = new Row(i, color);
                if ((i % 2) == 0) {
                    row = new Row(i, color);
                }
                //add that space to the ArrayList
                rows.add(row);
            }
        }else{
            for (i = 0; i < 8; i++) {
                //create a new space
                COLOR color;
                if (i < 4) {
                    color = COLOR.RED;
                    currentColor = color;
                } else {
                    color = COLOR.WHITE;
                    currentColor = color;
                }
                Row row = new Row(i, color);
                if ((i % 2) == 0) {
                    row = new Row(i, color);
                }
                //add that space to the ArrayList
                rows.add(row);
            }
        }
    }


    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    public ArrayList<Row> getRows(){
        return rows;
    }
}
