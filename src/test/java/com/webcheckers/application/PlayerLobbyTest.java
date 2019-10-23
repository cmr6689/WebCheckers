package com.webcheckers.application;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author <a href='mailto:cmr6689@rit.edu'>Cameron Riu</a>
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     * Test if adding a valid player is successful
     */
    @Test
    public void testValidAddPlayer() {
        final PlayerLobby playerLobby = new PlayerLobby();
        Player player = new Player("name");
        assertTrue(playerLobby.addPlayer(player));
    }

    /**
     * Test if adding the same player twice does not work
     */
    @Test
    public void testInvalidAddPlayer() {
        final PlayerLobby playerLobby = new PlayerLobby();
        Player player = new Player("name");
        playerLobby.addPlayer(player);
        Player player1 = new Player("name");
        assertFalse(playerLobby.addPlayer(player1));
    }

    /**
     * Test is setting the invalid name works
     */
    @Test
    public void testSetInvalidName() {
        final PlayerLobby playerLobby = new PlayerLobby();
        playerLobby.setInvalidName(true);
        assertTrue(playerLobby.getInvalidName());
    }

    /**
     * Test if getting the array of players is accurate
     */
    @Test
    public void testGetPlayers() {
        final PlayerLobby playerLobby = new PlayerLobby();
        playerLobby.addPlayer(new Player("name1"));
        playerLobby.addPlayer(new Player("name2"));
        ArrayList<Player> arrayList = new ArrayList<Player>();
        arrayList.add(new Player("name1"));
        arrayList.add(new Player("name2"));
        assertEquals(playerLobby.getPlayers(), arrayList);
    }
}
