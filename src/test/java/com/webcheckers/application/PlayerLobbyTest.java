package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author <a href='mailto:cmr6689@rit.edu'>Cameron Riu</a>
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     * Test the constructor
     */
    @Test
    void ctor_withArg(){
        GameCenter gameCenter = new GameCenter();
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        gameCenter.newGame(test1,test2);
        final PlayerLobby CuT = new PlayerLobby(gameCenter);
        assertFalse(CuT.getInvalidName());
        assertEquals(gameCenter, CuT.getGameCenter());
    }

    /**
     * Test if adding a valid player is successful
     */
    @Test
    public void testValidAddPlayer() {
        final PlayerLobby playerLobby = new PlayerLobby(new GameCenter());
        Player player = new Player("name");
        assertTrue(playerLobby.addPlayer(player));
        Player p2 = new Player(null);
        assertFalse(playerLobby.addPlayer(p2));
    }

    /**
     * Test if adding the same player twice does not work
     */
    @Test
    public void testInvalidAddPlayer() {
        final PlayerLobby playerLobby = new PlayerLobby(new GameCenter());
        Player player = new Player("name");
        playerLobby.addPlayer(player);
        Player player1 = new Player("name");
        assertFalse(playerLobby.addPlayer(player1));
    }

    /**
     * Test is setting the invalid name works
     */
    @Test
    public void setInvalidNameTest() {
        final PlayerLobby playerLobby = new PlayerLobby(new GameCenter());
        playerLobby.setInvalidName(true);
        assertTrue(playerLobby.getInvalidName());
        playerLobby.setInvalidName(false);
        assertFalse(playerLobby.getInvalidName());
    }

    /**
     * Test getting if the name is invalid
     */
    @Test
    public void getInvalidNameTest() {
        final PlayerLobby playerLobby = new PlayerLobby(new GameCenter());
        playerLobby.setInvalidName(true);
        assertEquals(true,playerLobby.getInvalidName());
        playerLobby.setInvalidName(false);
        assertEquals(false, playerLobby.getInvalidName());
    }

    /**
     * Test if getting the array of players is accurate
     */
    @Test
    public void getPlayersTest() {
        final PlayerLobby playerLobby = new PlayerLobby(new GameCenter());
        playerLobby.addPlayer(new Player("name1"));
        playerLobby.addPlayer(new Player("name2"));
        ArrayList<Player> arrayList = new ArrayList<>();
        arrayList.add(new Player("name1"));
        arrayList.add(new Player("name2"));
    }

    /**
     * Test if getting the array of available players is accurate
     */
    @Test
    public void getAvaPlayersTest() {
        GameCenter gameCenter = new GameCenter();
        final PlayerLobby playerLobby = new PlayerLobby(gameCenter);
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        Player test3 = new Player("Test3");
        Player test4 = new Player("Test4");
        Player ai = new Player("AI Player");
        playerLobby.addPlayer(test1);
        playerLobby.addPlayer(test2);
        playerLobby.addPlayer(test3);
        playerLobby.addPlayer(test4);
        gameCenter.newGame(test1,test2);
        ArrayList<Player> arrayList = new ArrayList<>();
        arrayList.add(ai);
        arrayList.add(test3);
        arrayList.add(test4);
        assertEquals(arrayList, playerLobby.getAvaPlayers());
        ArrayList<Player> arrayList2 = new ArrayList<>();
        gameCenter.endGame(test1, test2);
        arrayList2.add(ai);
        arrayList2.add(test1);
        arrayList2.add(test2);
        arrayList2.add(test3);
        arrayList2.add(test4);
        assertEquals(arrayList2, playerLobby.getAvaPlayers());
    }

    /**
     * Test the get game method
     */
    @Test
    public void getGameTest(){
        GameCenter gameCenter = new GameCenter();
        final PlayerLobby playerLobby = new PlayerLobby(gameCenter);
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        Player test3 = new Player("Test3");
        Player test4 = new Player("Test4");
        Game game = new Game(test1,test2);
        gameCenter.newGame(test1,test2);
        assertNull(playerLobby.getGame(test3));
        assertEquals(game.toString(),playerLobby.getGame(test1).toString());
    }

    /**
     * Test the getGameCenter() method
     */
    @Test
    public void getGameCenterTest(){
        GameCenter gameCenter = new GameCenter();
        final PlayerLobby playerLobby = new PlayerLobby(gameCenter);
        Player test1 = new Player("Test1");
        Player test2 = new Player("Test2");
        Game game = new Game(test1,test2);
        gameCenter.newGame(test1,test2);
        assertEquals(gameCenter.toString(),playerLobby.getGameCenter().toString());
    }
}
