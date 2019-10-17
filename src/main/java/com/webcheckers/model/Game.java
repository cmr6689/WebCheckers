package com.webcheckers.model;

public class Game {

    private Player player1;
    private Player player2;
    private BoardView boardView1;
    private BoardView boardView2;

    public Game (Player p1, Player p2){
        //boardView = new BoardView();
        boardView1 = new BoardView(1);
        boardView2 = new BoardView(2);
        this.player1 = p1;
        this.player2 = p2;
    }

    public BoardView getBoardView1() {
        return boardView1;
    }

    public BoardView getBoardView2() {
        return boardView2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer(Player p){
        if(player1 != null)
            player2 = p;
        else
            player1 = p;
    }

    public BoardView getGame(Player player) {
        if (player.equals(player2)) {
            return boardView2;
        }
        return boardView1;
    }
}
