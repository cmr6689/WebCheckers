package com.webcheckers.ui;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveChecks {

    private Game game;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();
    private HashMap<Position, Piece> positionPieceHashMap = new HashMap<>();

    private ArrayList<Move> moves = new ArrayList<>();
    private ArrayList<Move> jumps = new ArrayList<>();

    ValidateMove validateMove;

    public MoveChecks(Game game){
        this.game = game;
    }

    public ArrayList<Move> checkSinglePieceMoves(Piece piece, Position position){
        positions.clear();
        ArrayList<Move> moves = new ArrayList<>();

        ArrayList<Row> tempRows = new ArrayList<>();
        for(Row row : game.getBoardView1().getRows()){
            tempRows.add(row);
        }
        BoardView tempBoard = new BoardView(tempRows, game.getPlayer1());

        validateMove = new ValidateMove(tempBoard);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getBoardColor().equals(Space.BOARD_COLOR.BLACK))
                    positions.add(new Position(i, j));
            }
        }

        for(Position position2 : positions){
            Move move = new Move(position, position2);
            ArrayList<Position> temp = new ArrayList<>();
            if(validateMove.Validator(piece,
                    move,
                    piece.getType(),
                    piece.getColor(),
                    temp)
            ) {
                moves.add(move);
            }
        }

        return moves;
    }

    public ArrayList<Move> checkSinglePieceJumps(Piece piece, Position position){
        positions.clear();
        ArrayList<Move> jumps = new ArrayList<>();

        ArrayList<Row> tempRows = new ArrayList<>();
        for(Row row : game.getBoardView1().getRows()){
            tempRows.add(row);
        }
        BoardView tempBoard = new BoardView(tempRows, game.getPlayer1());

        validateMove = new ValidateMove(tempBoard);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getBoardColor().equals(Space.BOARD_COLOR.BLACK))
                    positions.add(new Position(i, j));
            }
        }

        for(Position position2 : positions){
            Move move = new Move(position, position2);
            ArrayList<Position> temp = new ArrayList<>();
            if(validateMove.Validator(piece,
                    move,
                    piece.getType(),
                    piece.getColor(),
                    temp)
            ) {
                if(!temp.isEmpty()){
                    jumps.add(move);
                }
            }
        }

        return jumps;
    }

    public boolean checkMoves(){
        Piece piece;

        moves.clear();
        jumps.clear();
        pieces.clear();
        positions.clear();
        positionPieceHashMap.clear();

        ArrayList<Row> tempRows = new ArrayList<>();
        for(Row row : game.getBoardView1().getRows()){
            tempRows.add(row);
        }
        BoardView tempBoard = new BoardView(tempRows, game.getPlayer2());

        validateMove = new ValidateMove(tempBoard);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null) {
                    piece = this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece();
                    pieces.add(piece); //adds all of the pieces to the arraylists
                    positionPieceHashMap.put(new Position(i, j), piece);
                }
                if(this.game.getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getBoardColor().equals(Space.BOARD_COLOR.BLACK))
                    positions.add(new Position(i, j));
            }
        }

        for(Position position : positionPieceHashMap.keySet()){
            for(Position position2 : positions){
                Move move = new Move(position, position2);
                ArrayList<Position> temp = new ArrayList<>();
                if(validateMove.Validator(positionPieceHashMap.get(position),
                        move,
                        positionPieceHashMap.get(position).getType(),
                        positionPieceHashMap.get(position).getColor(),
                        temp)
                ) {
                    moves.add(move);
                    if(!temp.isEmpty())
                        jumps.add(move);
                }
            }
        }
        return !moves.isEmpty();
    }

    public boolean moveAvailable(){
        return !moves.isEmpty();
    }

    public boolean jumpAvailable(){
        return !jumps.isEmpty();
    }

    public ArrayList<Move> getMoves (){ return moves; }

    public ArrayList<Move> getJumps() {
        return jumps;
    }
}
