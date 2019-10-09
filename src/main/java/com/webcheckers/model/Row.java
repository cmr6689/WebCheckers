package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable{

    //integer from 0 to 7 indicating the space in the row
    private int index;

    //creates an array list to hold all the spaces in this row
    private ArrayList<Space> spaces = new ArrayList<Space>();

    public Row(int index){
        this.index = index;
        for(int i = 0; i < 8; i++){
            //create a new space
            Space space = new Space(null,i);
            //add that space to the ArrayList
            spaces.add(space);
        }
    }

    /**
     * Class needed in order for the iterator to work
     * @return the iterator of the spaces
     */
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

    /**
     * @return The index of this row within the board
     */
    public int getIndex() {
        return index;
    }
}
