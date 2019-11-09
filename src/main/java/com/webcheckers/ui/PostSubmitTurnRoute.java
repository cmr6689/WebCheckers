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
 * This class handles the ajax call of /submitTurn and responds with a json message
 *
 * @author Team-E
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostSubmitTurnRoute.class.getName());

    private final Gson gson;

    PlayerLobby playerLobby;

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
        //TODO Change turn by changing current user and active color
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
        Move move = httpSession.attribute("move");

        //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());

        if(playerLobby.getGameCenter().getGame(myPlayer).getMap().get("activeColor").equals("WHITE")) {
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer2());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView1());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        }else{
            //playerLobby.getGameCenter().getGame(myPlayer).getGameData().setCurrentUser(playerLobby.getGame(myPlayer).getPlayer1());
            //gameData.setBoard(playerLobby.getGameCenter().getGame(myPlayer).getBoardView2());
            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "WHITE");
        }

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();
        int thisRow = move.getStart().getRow();
        int thisCell = move.getStart().getCell();
        Piece thisPiece = board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).getPiece();
        //actually do the move given that it's valid on the board
        board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).setPiece(null);
        thisRow = move.getEnd().getRow();
        thisCell = move.getEnd().getCell();
        board.getRowAtIndex(thisRow).getSpaceAtIndex(thisCell).setPiece(thisPiece);
        board.resetMovs();

        // render the View
        return gson.toJson(message);
    }
}
