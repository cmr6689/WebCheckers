package com.webcheckers.model;

public class Game {

    //Player 1 of the game
    private Player player1;

    //Player 2 of the game
    private Player player2;

    //Respective boards veiw for each player
    private BoardView boardView1;
    private BoardView boardView2;

    //if the game is active
    private Boolean active = true;

    //constructor, needs player1 and player2
    public Game (Player p1, Player p2){
        //boardView = new BoardView();
        boardView1 = new BoardView(1);
        boardView2 = new BoardView(2);
        this.player1 = p1;
        this.player2 = p2;
    }

    //returns the board view for player 1
    public BoardView getBoardView1() {
        return boardView1;
    }

    //returns the board view for player 2
    public BoardView getBoardView2() {
        return boardView2;
    }

    //sets player 1
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    //sets player 2
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    //returns player 1
    public Player getPlayer1(){
        return this.player1;
    }

    //returns player 2
    public Player getPlayer2(){
        return this.player2;
    }

    //gets the name of player1
    public String getPlayer1Name(){
        return this.player1.getName();
    }

    //gets the game of player2
    public String getPlayer2Name(){
        return this.player2.getName();
    }

    //returns if the game is active
    public Boolean isActive(){
        return this.active;
    }

    //sets the IsActive, takes a boolean
    public void setIsActive(Boolean bool){
        active = bool;
    }

    //sets the player, if p1 is null then will set p2
    public void setPlayer(Player p){
        if(player1 != null)
            player2 = p;
        else
            player1 = p;
    }

    //returns the game board view based on the player given
    //if it is p1 then returns board view 1
    //if it is p2 then returns board view 2
    public BoardView getGame(Player player) {
        if (player.equals(player2)) {
            return boardView2;
        }
        return boardView1;
    }


    @Override
    public String toString() {
        return player1.getName() + " vs " + player2.getName();
    }
}
