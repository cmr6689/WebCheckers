package com.webcheckers.application;

import com.webcheckers.model.BoardView;

public class GameCenter {

    private BoardView boardView;

    public GameCenter (){
        //boardView = new BoardView();
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void startGame(){
        boardView = new BoardView();
    }
}
