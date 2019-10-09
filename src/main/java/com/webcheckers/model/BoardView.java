package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable{
    //create a new ArrayList of Rows
    private ArrayList<Row> rows = new ArrayList<Row>();

    public BoardView(){
        for(int i = 0; i < 8; i++){
            //create a new space
            Row row = new Row(i);
            //add that space to the ArrayList
            rows.add(row);
        }
    }

    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }
}
