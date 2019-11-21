package com.webcheckers.ui;

import com.webcheckers.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class checks the game board for valid moves, jumps, and/or multiple jumps
 *
 * @author - Team E
 */
public class MoveChecks {

    private Game game;
    private ArrayList<Position> positions = new ArrayList<>();
    private HashMap<Position, Piece> positionPieceHashMap = new HashMap<>();

    private ArrayList<Move> moves = new ArrayList<>();
    private ArrayList<Move> jumps = new ArrayList<>();

   private ValidateMove validateMove;

    /**
     * Constructor for MoveChecks, requires the Game to get the board
     * @param game - game being played
     */
    public MoveChecks(Game game){
        this.game = game;
    }

    /**
     * Checks to see if any simple moves are available
     * @param piece being moved
     * @param position of the piece
     * @return an array list of possible simple moves
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

    /**
     * Checks to see if any jumps are available
     * @param piece being moved
     * @param position of that piece
     * @return an array list of possible jumps
     */
    public ArrayList<Move> checkSinglePieceJumps(Piece piece, Position position){
        ArrayList<Move> jumps = new ArrayList<>();
        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<Row> tempRows = new ArrayList<>();

        if(game == null){
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

    /**
     * Check to see if there are any multi jumps available
     * @param move initial jump being made
     * @param piece being moved
     * @return an array list of possible multi jumps
     */
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

    /**
     * Checks the board for all available moves, jumps, and/or multi jumps
     * @return true if a move is available
     */
    public boolean checkMoves(){
        Piece piece;
        moves.clear();
        jumps.clear();
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

    /**
     * Getter for the move array list
     * @return moves list
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Getter for the jump array list
     * @return jumps list
     */
    public ArrayList<Move> getJumps() {
        return jumps;
    }

    /**
     * Checks to see if the red player can jump
     * @return true if jump available
     */
    public boolean redCanJump() {
        for (Move jump : jumps) {
            if (game.getBoardView1().getRowAtIndex(jump.getStart().getRow()).getSpaceAtIndex(jump.getStart().getCell()).getPiece().getColor().equals(Piece.COLOR.RED)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the white player can jump
     * @return true if jump available
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
