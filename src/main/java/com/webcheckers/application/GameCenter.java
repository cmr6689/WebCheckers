package com.webcheckers.application;

import com.webcheckers.model.BoardView;

public class GameCenter {

    private BoardView boardView;

    public GameCenter (){
        boardView = new BoardView();
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
