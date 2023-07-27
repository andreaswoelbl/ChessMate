import NetObjects.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class LobbyRetrieveLobbyDataObjectTests {
    Lobby lobby;

    public void initLobby(){
        lobby = new Lobby();
        LobbyManager.sessions.add(lobby);
    }

    @DisplayName("initialRetrieveTest")
    @Test
    public void initialRetrieveTest(){
        initLobby();
        LobbyDataObject retrievedObject = lobby.retrieveLobbyDataObject();
        assertEquals(lobby.lobbyID,retrievedObject.getLobbyID());
        assertEquals(GameStates.WAITING_FOR_PLAYER,retrievedObject.getCurrentLobbyState());
        assertEquals(0,retrievedObject.getPlayercount());
        assertFalse(retrievedObject.isCheatFuncActive());
        assertFalse(retrievedObject.isClearLobby());
    }

    @DisplayName("initialPlayerOneRetrieveTest")
    @Test
    public void initialPlayerOneRetrieveTest(){
        initLobby();
        lobby.player1Join(null,"testName");
        LobbyDataObject retrievedObject = lobby.retrieveLobbyDataObject();
        // Player 1
        assertEquals("testName",retrievedObject.getPlayer1().getName());
        assertEquals(ChessPieceColour.WHITE,retrievedObject.getPlayer1().getChessPieceColour());
        assertEquals(3,retrievedObject.getPlayer1().getMaxWrongCheatReveal());
        // Player 2
        assertEquals("",retrievedObject.getPlayer2().getName());
        assertNull(retrievedObject.getPlayer2().getChessPieceColour());
        assertEquals(0,retrievedObject.getPlayer2().getMaxWrongCheatReveal());

        assertEquals(1, retrievedObject.getPlayercount());
    }

    @DisplayName("initialPlayerTwoRetrieveTest")
    @Test
    public void initialPlayerTwoRetrieveTest(){
        initLobby();
        lobby.player2Join(null,"testName");
        LobbyDataObject retrievedObject = lobby.retrieveLobbyDataObject();
        // Player 1
        assertEquals("",retrievedObject.getPlayer1().getName());
        assertNull(retrievedObject.getPlayer1().getChessPieceColour());
        assertEquals(0,retrievedObject.getPlayer1().getMaxWrongCheatReveal());
        // Player 2
        assertEquals("testName",retrievedObject.getPlayer2().getName());
        assertEquals(ChessPieceColour.BLACK,retrievedObject.getPlayer2().getChessPieceColour());
        assertEquals(3,retrievedObject.getPlayer2().getMaxWrongCheatReveal());

        assertEquals(1, retrievedObject.getPlayercount());
    }

    @DisplayName("initialTwoPlayerRetrieveTest")
    @Test
    public void initialTwoPlayerRetrieveTest(){
        initLobby();
        lobby.player1Join(null,"testName");
        lobby.player2Join(null,"testName");
        LobbyDataObject retrievedObject = lobby.retrieveLobbyDataObject();
        // Player 1
        assertEquals("testName",retrievedObject.getPlayer1().getName());
        assertEquals(ChessPieceColour.WHITE,retrievedObject.getPlayer1().getChessPieceColour());
        assertEquals(3,retrievedObject.getPlayer1().getMaxWrongCheatReveal());
        // Player 2
        assertEquals("testName",retrievedObject.getPlayer2().getName());
        assertEquals(ChessPieceColour.BLACK,retrievedObject.getPlayer2().getChessPieceColour());
        assertEquals(3,retrievedObject.getPlayer2().getMaxWrongCheatReveal());

        assertEquals(2, retrievedObject.getPlayercount());
    }

}
