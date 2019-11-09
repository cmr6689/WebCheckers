package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /validateMove and responds with a json message
 *
 * @author Team-E
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostValidateMoveRoute.class.getName());

    private final Gson gson;

    private PlayerLobby playerLobby;

    private GameData gameData;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param gameData the vm map data of the game
     * @param playerLobby the lobby of all the players
     */
    public PostValidateMoveRoute(PlayerLobby playerLobby, GameData gameData){
        this.gson = new Gson();
        this.playerLobby = playerLobby;
        this.gameData = gameData;
    }

    /**
     * needs to check the space at that position and look to see if it is a valid space, black and no piece on it
     * @return
     */
    public boolean positionIsValid(BoardView board, int row, int cell){
        System.err.println(board + " " + row);
        Row actualRow = board.getRowAtIndex(row);
        Space actualSpace = actualRow.getSpaceAtIndex(cell);
        //check to see if the piece has already been moved once
        if(board.getNumMovs() > 0){
            return false;
        }
        //if the space is a valid spot to move to
        if(actualSpace.isValid()){
            return true;
        }
        return false;
    }

    /**
     * checks whether or not the move is valid
     * @param move the move being made
     * @param thisPiece the piece used in the move
     * @return true if a valid move
     */
    public boolean moveIsValid(BoardView board, Move move, Piece thisPiece){
        Piece.TYPE type;
        type = thisPiece.getType();
        if(board.getNumMovs() > 1){
            return false;
        }
        if(move.getStart().getCell() == move.getEnd().getCell()){
            return false;
        }
        if(Math.abs(move.getStart().getRow()-move.getEnd().getRow()) > 1){
            //set the row and the cell of the piece being jumped
            int tempRowInt = ((move.getStart().getRow() + move.getEnd().getRow()) / 2);
            int tempCellInt = ((move.getStart().getCell() + move.getEnd().getCell()) / 2);
            Piece tempPiece = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece();
            if(tempPiece == null) {
                //the move can't be over 1 row if it's not jumping a piece
                return false;
            }
        }
        if(type == Piece.TYPE.SINGLE && !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
            //if it's not king it cannot move backwards
            return false;
        }
        return true;
    }

    /**
     * check if the piece is trying to jump and if the jump is valid
     * true if the piece isn't trying to jump
     * false if there is a piece in the end destination
     * @return
     */
    public boolean jumpIsValid(int rowsBeingJumped, Move move,
                               BoardView board, Piece thisPiece, int row, int cell){
        int tempRowInt;
        int tempCellInt;
        Piece.TYPE type;
        Piece.COLOR color;
        if(!(positionIsValid(board, row, cell)) || (rowsBeingJumped >= 3)){
            return false;
        }else{
            if(rowsBeingJumped>1) {
                //set the row and the cell of the piece being jumped
                tempRowInt = ((move.getStart().getRow() + move.getEnd().getRow()) / 2);
                tempCellInt = ((move.getStart().getCell() + move.getEnd().getCell()) / 2);
                //get the color of the piece at the location
                color = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (color != thisPiece.getColor()) {
                    if (type == Piece.TYPE.SINGLE && !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
                        //if it's not king it cannot move backwards
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }
            else{
                return true;
            }
        }
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");

        LOG.finer("PostValidateMoveRoute is invoked.");

        System.out.println(request.queryParams("actionData"));

        request.queryParams("gameID");
        Move move = gson.fromJson(request.queryParams("actionData"), Move.class);
        vm.put("title", "Loser");

        ResponseMessage message = new ResponseMessage();
        // to validate a move, replace message type of ERROR with INFO

        //validate move using position and board view here
        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        int thisRow = move.getStart().getRow();
        int thisCell = move.getStart().getCell();
        Piece thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
        int rowsBeingJumped = Math.abs(move.getStart().getRow() - move.getEnd().getRow());

        boolean isValid = positionIsValid(board, move.getEnd().getRow(), move.getEnd().getCell()) &&
                moveIsValid(board, move, thisPiece) &&
                jumpIsValid(rowsBeingJumped, move, board, thisPiece, move.getEnd().getRow(), move.getEnd().getCell());

        if(isValid) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your move is valid");
            httpSession.attribute("move", move);
            //increase the number of movs this turn
            board.increaseNumMovs();
            if(thisRow == 7){
                thisPiece.kingPiece();
            }
        }else{
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("Your move is not valid");
        }


        // render the View
        return gson.toJson(message);
    }
}
