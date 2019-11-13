package com.webcheckers.ui;

import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;

import java.util.ArrayList;

public class MoveChecks {

    private Game game;
    private ArrayList<Piece> pieces = new ArrayList<>();

    public MoveChecks(Game game){
        Piece piece;
        this.game = game;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null) {
                    piece = this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece();
                    this.pieces.add(piece);
                }
            }
        }
    }

    public boolean jumpAvailable(){
        return false;
    }
}
