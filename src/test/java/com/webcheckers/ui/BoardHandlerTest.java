package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;


@Tag("UI-tier")
public class BoardHandlerTest {

    private BoardHandler CuT;
    private BoardView board;

    @BeforeEach
    public void testSetup() {
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i, Piece.COLOR.RED));
        }
        board = new BoardView(rows, mock(Player.class));
        CuT = new BoardHandler(board);
    }

    @Test
    public void ctor(){
        assertNotNull(CuT, "The Constructor is Null and should not be");
    }


    @Test
    public void set_board(){
        board.setFinalPos(new Position(0, 0));
        Position end = board.getFinalPos();
        board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).setPiece(new Piece(Piece.COLOR.RED, Piece.TYPE.SINGLE));
        Piece thisPiece = board.getRowAtIndex(end.getRow()).getSpaceAtIndex(end.getCell()).getPiece();

        assertEquals(thisPiece.getType(), Piece.TYPE.KING);
    }

}
