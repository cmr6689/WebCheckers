package com.webcheckers.model;

public class Row {

    //integer from 0 to 7
    private int index;

    public Row(int index){
        this.index = index;
    }

    /**
     * @return The index of this row within the board
     */
    public int getIndex() {
        return index;
    }
}
