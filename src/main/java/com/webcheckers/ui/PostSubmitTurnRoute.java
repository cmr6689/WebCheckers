package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.*;
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


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby the lobby of all the players
     */
    public PostSubmitTurnRoute(PlayerLobby playerLobby) {
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    /**
     * Respond to the ajax call with a gson to json message
     *
     * @param request  the HTTP request
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


        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        ValidateMove MoveValidator = httpSession.attribute("validator");
        MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));
        System.out.println(moveCheck.jumpAvailable());

        ResponseMessage message;
        //Check if valid move first
        if(moveCheck.jumpAvailable()) {
            message = new ResponseMessage();
            // to successfully resign, replace message type of ERROR with INFO
            message.setType(ResponseMessage.MessageType.ERROR);
            message.setText("You can not submit a turn in the state you are in.");
        }else{
            message = new ResponseMessage();
            // to successfully resign, replace message type of ERROR with INFO
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText("You can submit a turn in the state you are in.");
        }

        //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());

        if (playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor").equals("WHITE")) {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView1());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        } else {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView2());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "WHITE");
        }

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        Position start = board.getOriginalPos();
        Position end = board.getFinalPos();
        removedPs = board.getRemovedPieces();
        Piece thisPiece = board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).getPiece();
        board.getRowAtIndex(start.getRow()).getSpaceAtIndex(start.getCell()).removePiece();
//        if(board.getRowAtIndex(end.getRow()).equals(board.getRowAtIndex(7)) && thisPiece.getColor().equals(Piece.COLOR.RED) &&
//                !thisPiece.getType().equals(Piece.TYPE.KING)){
//            thisPiece.setType(Piece.TYPE.KING);
//        }
        board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(thisPiece);
        //System.out.println(board.getRemovedPieces().size());
        if(removedPs.size() != 0){
            System.out.println(board.getRemovedPieces().size());
            for(Position p: removedPs){
                board.getRowAtIndex(p.getRow()).getSpaceAtIndex(p.getCell()).removePiece();
            }
        }
        board.resetPositions();
        board.clearMovesThisTurn();
        board.clearRemovedPieces();
        board.resetMovs();
        return gson.toJson(message);
    }
}
