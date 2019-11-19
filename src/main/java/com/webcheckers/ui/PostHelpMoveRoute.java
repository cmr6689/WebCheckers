package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import spark.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the ajax call of /resign and responds with a json message
 *
 * @author Team-E
 */
public class PostHelpMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostResignRoute.class.getName());

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param playerLobby the lobby of all the players
     */
    public PostHelpMoveRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        this.gson = new Gson();
    }

    public String getRedMove(Player myPlayer) {
        MoveChecks moveChecks = new MoveChecks(playerLobby.getGame(myPlayer));
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                //if piece is not null and red
                if (playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE))
                        || playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.RED, Piece.TYPE.KING)))) {
                    //if jump is available
                    if (moveChecks.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                    new Position(i, j)).size() > 0) {
                        //get first jump in returned list
                        Move jump = moveChecks.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        //return first jump
                        return jump.getStart().helpString() + " -> " + jump.getEnd().helpString();

                        //if no jump is available check simple moves
                    } else if (moveChecks.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        //get first move in returned list
                        Move move = moveChecks.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoard(myPlayer).getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        //return first move
                        return move.getStart().helpString() + " -> " + move.getEnd().helpString();
                    }
                }
            }
        }
        //no jumps or moves available
        return "No moves available!";
    }

    public String getWhiteMove(Player myPlayer) {
        MoveChecks moveChecks = new MoveChecks(playerLobby.getGame(myPlayer));
        //loop through rows
        for (int i = 0; i < 8; i++) {
            //loop through spaces
            for (int j = 0; j < 8; j++) {
                if (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                        (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                || playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                    if (moveChecks.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move jump = moveChecks.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        return jump.getStart().helpString2() + " -> " + jump.getEnd().helpString2();
                    } else if (moveChecks.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                            new Position(i, j)).size() > 0) {
                        Move move = moveChecks.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                new Position(i, j)).get(0);
                        return move.getStart().helpString2() + " -> " + move.getEnd().helpString2();
                    }
                }
            }
        }
        return "No moves available!";
    }

    /**
     * Respond to the ajax call with a gson to json message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the json message
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player myPlayer = httpSession.attribute("player");
        LOG.config("PostHelpTurn is invoked by " + myPlayer.getName() + ".");
        if (myPlayer.equals(playerLobby.getGame(myPlayer).getPlayer1())) {
            ResponseMessage message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText(getRedMove(myPlayer));
            return gson.toJson(message);
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setType(ResponseMessage.MessageType.INFO);
            message.setText(getWhiteMove(myPlayer));
            return gson.toJson(message);
        }
    }
}
