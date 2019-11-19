package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Route that handles the ajax call of checking whose turn it is while it is currently not your turn.
 *
 * @author Team-E
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(com.webcheckers.ui.PostCheckTurnRoute.class.getName());

    public final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = new Gson();
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
        LOG.config("PostCheckTurn is invoked by " + myPlayer.getName() + ".");

        if(playerLobby.getGameCenter().getGame(myPlayer) != null && playerLobby.getGameCenter().getGame(myPlayer).getPlayer2().getName().equals("AI")){
            MoveChecks moveCheck = new MoveChecks(playerLobby.getGameCenter().getGame(myPlayer));
                //loop through rows
                for (int i = 0; i < 8; i++) {
                    //loop through spaces
                    for (int j = 0; j < 8; j++) {
                        if (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece() != null &&
                                (playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE))
                                        || playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece().equals(new Piece(Piece.COLOR.WHITE, Piece.TYPE.KING)))) {
                            if (moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                    new Position(i, j)).size() > 0) {
                                Move jump = moveCheck.checkSinglePieceJumps(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                        new Position(i, j)).get(0);
                                playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(jump.getEnd().getRow()).getSpaceAtIndex(jump.getEnd().getCell()).removePiece();
                                playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(jump.getEnd().getRow()).getSpaceAtIndex(jump.getEnd().getCell()).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
                                break;
                            } else if (moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                    new Position(i, j)).size() > 0) {
                                Move move = moveCheck.checkSinglePieceMoves(playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(i).getSpaceAtIndex(j).getPiece(),
                                        new Position(i, j)).get(0);
                                playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(move.getStart().getRow()).getSpaceAtIndex(move.getStart().getCell()).removePiece();
                                playerLobby.getGame(myPlayer).getBoardView1().getRowAtIndex(move.getEnd().getRow()).getSpaceAtIndex(move.getEnd().getCell()).setPiece(new Piece(Piece.COLOR.WHITE, Piece.TYPE.SINGLE));
                                break;
                            }
                        }
                    }
                }

            playerLobby.getGameCenter().getGame(myPlayer).getMap().put("activeColor", "RED");
        }
        //for the player that needs to refresh when the game has ended
        if (playerLobby.getGameCenter().justEnded(myPlayer)) {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("true");
            return gson.toJson(message2);

            //game is no longer active
        }else if (playerLobby.getGameCenter().getGame(myPlayer) == null || !playerLobby.getGameCenter().getGame(myPlayer).isActive()) {
            ResponseMessage message1 = new ResponseMessage();
            message1.setType(ResponseMessage.MessageType.INFO);
            message1.setText("false");
            return gson.toJson(message1);
        } else {
            ResponseMessage message2 = new ResponseMessage();
            message2.setType(ResponseMessage.MessageType.INFO);
            message2.setText("true");
            return gson.toJson(message2);
        }
    }
}