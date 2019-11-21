package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The UI controller for the /backupMove ajax call during a game
 *
 * @author - Team E
 */
public class PostBackupMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostBackupMoveRoute.class.getName());

    private final Gson gson;

    private PlayerLobby playerLobby;

    /**
     * Constructor to set the Gson and the player lobby
     * @param playerLobby - holder of game center and players
     */
    public PostBackupMoveRoute(PlayerLobby playerLobby){
        this.gson = new Gson();
        this.playerLobby = playerLobby;
    }

    /**
     * Respond to the ajax call with a Gson to Json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        LOG.config("PostBackupMoveRoute is invoked by " + myPlayer.getName() + ".");

        BoardView board = playerLobby.getGame(myPlayer).getBoardView1();

        if(board.getMovesThisTurn().size() >= 1) {
            board.resetMovs();
            board.resetPositions();
            board.clearMovesThisTurn();
            board.setLastWasJump(false);
            board.clearRemovedPieces();
        }

        ResponseMessage message = new ResponseMessage();
        // to back up a move, replace message type of ERROR with INFO
        message.setType(ResponseMessage.MessageType.INFO);
        message.setText("Your move has been backed up");

        // render the View
        return gson.toJson(message);
    }
}
