package com.webcheckers.model;

import java.lang.reflect.Array;

public class Row {

    //integer from 0 to 7
    private int index;
    private Space spacesArray[];

    public Row(int index){
        this.index = index;
        spacesArray = new Space[8];
        for(int i = 0; i <= 7; i++){
            Space space = new Space(null,i);
            spacesArray[i] = space;
        }
    }

    /**
     * @return The index of this row within the board
     */
    public int getIndex() {
        return index;
    }
}
