package com.webcheckers.ui;

import com.webcheckers.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MoveChecks {

    private Game game;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();
    private HashMap<Position, Piece> positionPieceHashMap = new HashMap<>();

    private ArrayList<Move> moves = new ArrayList<>();
    private ArrayList<Move> jumps = new ArrayList<>();

    ValidateMove validateMove;

    /*
    constructor for MoveChecks, requires the Game to get the board
     */
    public MoveChecks(Game game){
        this.game = game;
    }

    /*
    given a piece, and a position
    returns an arrayList of moves (including jumps) that can be made from the piece and position
     */
    public ArrayList<Move> checkSinglePieceMoves(Piece piece, Position position){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Position> positions = new ArrayList<>();

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


    /*
    given a piece, and a position
    returns an arrayList of jumps (only jumps)  that can be made from the piece and position
     */
    public ArrayList<Move> checkSinglePieceJumps(Piece piece, Position position){
        ArrayList<Move> jumps = new ArrayList<>();
        ArrayList<Position> positions = new ArrayList<>();

        ArrayList<Row> tempRows = new ArrayList<>();

        if(game == null){
            System.err.println("raa game is bad");
            return jumps;
        }

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

    public ArrayList<Move> getJumpChain(Move move, Piece piece){
        ArrayList<Move> jumpChain = new ArrayList<>();

        ArrayList<Move> movesTemp = checkSinglePieceJumps(piece, move.getEnd());
        HashSet<Move> exsistingMoves = new HashSet<>();

        while(!movesTemp.isEmpty()) {

            if(exsistingMoves.contains(movesTemp.get(0))){
                break;
            }

            //checking for flipped moves
            for(Move move1 : jumpChain){
                Move tempM = movesTemp.get(0);
                if(move1.getStart().equals(tempM.getEnd()) && move1.getEnd().equals(tempM.getStart())){
                    break;
                }
            }

            jumpChain.add(movesTemp.get(0));
            exsistingMoves.add(movesTemp.get(0));
            movesTemp = checkSinglePieceJumps(piece, movesTemp.get(0).getEnd());

        }


        return jumpChain;
    }

    /*
    checks all of the possible moves that be made on the board, returns if any moves can be made
     */
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
        BoardView tempBoard = new BoardView(tempRows, game.getPlayer1());

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
                    if(!temp.isEmpty()) {
                        jumps.add(move);
                    }
                }
            }
        }
        return !moves.isEmpty();
    }

    /*
    returns the moves that can be made
     */
    public ArrayList<Move> getMoves (){ return moves; }

    /*
    returns the jumps that can be moved
     */
    public ArrayList<Move> getJumps() {
        return jumps;
    }

    /*
    returns if the red player can make a jump
     */
    public boolean redCanJump() {
        for (Move jump : jumps) {
            if (game.getBoardView1().getRowAtIndex(jump.getStart().getRow()).getSpaceAtIndex(jump.getStart().getCell()).getPiece().getColor().equals(Piece.COLOR.RED)) {
                return true;
            }
        }
        return false;
    }

    /*
    returns if a white player can make a jump
     */
    public boolean whiteCanJump() {
        for (Move jump : jumps) {
            if (game.getBoardView1().getRowAtIndex(jump.getStart().getRow()).getSpaceAtIndex(jump.getStart().getCell()).getPiece().getColor().equals(Piece.COLOR.WHITE)) {
                return true;
            }
        }
        return false;
    }
}
