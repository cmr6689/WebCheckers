package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /submitTurn and responds with a json message
 *
 * @author Team-E
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostSubmitTurnRoute.class.getName());

    private final Gson gson;

    PlayerLobby playerLobby;

    ArrayList<Position> removedPs = new ArrayList<>();

    ArrayList<Move> allMoves = new ArrayList<>();

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby the lobby of all the players
     */
    public PostSubmitTurnRoute(PlayerLobby playerLobby){
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        //TODO check if jump is available
        System.out.println("Called Submit");
        Map<String, Object> vm = new HashMap<>();

        LOG.finer("PostSubmitTurnRoute is invoked.");

        System.err.println("turn was submitted");

        request.queryParams("gameID");
        vm.put("title", "Webcheckers");

        //Check if valid move first
        ResponseMessage message = new ResponseMessage();
        // to successfully resign, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("You can not submit a turn in the state you are in.");

        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        ValidateMove MoveValidator = httpSession.attribute("validator");
        Move move = httpSession.attribute("move");
        Position start = new Position(move.getStart().getRow(),move.getStart().getCell());
        Position end = new Position(move.getEnd().getRow(),move.getEnd().getCell());
        Boolean jumped = httpSession.attribute("jumped");

        //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());

        if(playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor").equals("WHITE")) {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView1());
            playerLobby.getGameCenter().getGame(myPlayer).checkGameOver();
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        }else{
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView2());
            playerLobby.getGameCenter().getGame(myPlayer).checkGameOver();
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "WHITE");
        }

        removedPs = MoveValidator.getRemovedPieces();
        allMoves = MoveValidator.getMovesThisTurn();
        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        for(int i = 0; i < allMoves.size(); i++) {
            move = allMoves.get(i);
            start = new Position(move.getStart().getRow(),move.getStart().getCell());
            end = new Position(move.getEnd().getRow(),move.getEnd().getCell());
            //BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
            int thisRow = start.getRow();
            int thisCell = start.getCell();
            Piece thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
            //actually do the move given that it's valid on the board
            //if it only moved once do this
            board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).setPiece(null);
            thisRow = end.getRow();
            thisCell = end.getCell();
            board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).setPiece(thisPiece);
            /*
            if (removedPs.size() != 0) {
                //if there is a jump or multiple jump remove each piece jumped
                for (Position p : removedPs) {
                    board.getRowAtIndex(p.getRow()).getSpaceAtIndex(p.getCell()).removePiece();
                }
                MoveValidator.clearRemovedPieces();
            }
            */
            if (jumped) {
                //remove the piece
                thisRow = ((start.getRow() + end.getRow()) / 2);
                thisCell = ((start.getCell() + end.getCell()) / 2);
                board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).setPiece(null);
            }
            if (end.getRow() == 0 || end.getRow() == 7) {
                //king the piece
                thisRow = end.getRow();
                thisCell = end.getCell();
                board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece().kingPiece();
            }
        }
        board.resetMovs();
        MoveValidator.clearMovesThisTurn();
        // render the View
        return gson.toJson(message);
    }
}
