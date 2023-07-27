import NetObjects.GameStates;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class LobbyBasicTests {
    Lobby lobby;

    public void initLobby(){
        lobby = new Lobby();
        LobbyManager.sessions.add(lobby);
    }

    @DisplayName("LobbyInitTest")
    @Test
    public void lobbyInitTest(){
        initLobby();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

    @DisplayName("updateLobbyOnPlayerCountZeroTest")
    @Test
    public void updateLobbyOnPlayerCountZeroTest(){
        initLobby();
        lobby.updateLobby();
        assertTrue(LobbyManager.getSessions().isEmpty());
    }

    @DisplayName("updateLobbyOnPlayerCountTwoTest")
    @Test
    public void updateLobbyOnPlayerCountTwoTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player2Join(null, "testName");
        lobby.updateLobby();
        assertSame(lobby.currentLobbyState, GameStates.READY);
    }

}
