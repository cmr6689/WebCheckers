package com.webcheckers.model;

public class Game {

    private Player player1;
    private Player player2;
    private BoardView boardView1;
    private BoardView boardView2;
    private Boolean active;
    private Player turnP;

    public Game (Player p1, Player p2){
        //boardView = new BoardView();
        boardView1 = new BoardView(1);
        boardView2 = new BoardView(2);
        this.player1 = p1;
        this.player2 = p2;
        turnP = player1;
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

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player1;
    }

    public String getPlayer1Name(){
        return this.player1.getName();
    }

    public String getPlayer2Name(){
        return this.player2.getName();
    }

    public Boolean isActive(){
        return this.active;
    }

    public void setIsActive(Boolean bool){
        active = bool;
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

    public boolean isMoveValid(Player p, int mv){
        //checks if a move is valid from the given player and the space

        if(p != turnP)
            return false;



        return true;
    }

    public void makeMove(Player p, int mv){
        //updates both player boards with a valid mov

    }

    @Override
    public String toString() {
        return player1.getName() + " vs " + player2.getName();
    }
}
