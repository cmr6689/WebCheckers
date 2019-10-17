package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;

public class GameCenter {

    private Player player1;
    private Player player2;
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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer(Player p){
        if(player1 == null)
            player2 = p;
        else
            player1 = p;
    }
}
