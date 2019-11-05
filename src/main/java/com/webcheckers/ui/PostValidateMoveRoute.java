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
    public boolean moveIsValid(Move move, Piece thisPiece){
        Piece.TYPE type;
        type = thisPiece.getType();
        if(Math.abs(move.getStart().getRow()-move.getEnd().getRow()) > 1){
            return false;
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
    public boolean jumpIsValid(boolean jumpingPiece, int rowsBeingJumped, Move move,
                               BoardView board, Piece thisPiece, int row, int cell){
        int tempRowInt;
        int tempCellInt;
        Piece.TYPE type;
        Piece.COLOR color;
        if(jumpingPiece == true){
            if(!(positionIsValid(board, row, cell)) || (rowsBeingJumped >= 2)){
                return false;
            }else{
                //set the row and the cell of the piece being jumped
                tempRowInt = ((move.getStart().getRow() + move.getEnd().getRow()) / 2);
                tempCellInt = ((move.getStart().getCell() + move.getEnd().getCell()) / 2);
                //get the color of the piece at the location
                color = board.getRowAtIndex(tempRowInt).getSpaceAtIndex(tempCellInt).getPiece().getColor();
                //get the type of the piece at the location
                type = thisPiece.getType();
                //if the color is the same as the color being jumped return false
                if (color != thisPiece.getColor()) {
                    if(type == Piece.TYPE.SINGLE && !((move.getStart().getRow() - move.getEnd().getRow()) > 0)) {
                        //if it's not king it cannot move backwards
                        return false;
                    }
                    return true;
                }else{
                    return false;
                }
            }

        }else{
            return true;
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
        boolean jumpingPiece = (rowsBeingJumped != 1);

        boolean isValid = positionIsValid(board, move.getEnd().getRow(), move.getEnd().getCell()) &&
                jumpIsValid(jumpingPiece, rowsBeingJumped, move, board, thisPiece, move.getEnd().getRow(), move.getEnd().getCell()) &&
                moveIsValid(move, thisPiece);

        if(isValid) {
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("Your move is valid");
        }else{
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("Your move is not valid");
        }


        // render the View
        return gson.toJson(message);
    }
}
