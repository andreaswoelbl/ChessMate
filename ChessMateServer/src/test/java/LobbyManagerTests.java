import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class LobbyManagerTests {
    Lobby lobby;
    public void initLobby(){
        lobby = new Lobby();
        LobbyManager.sessions.add(lobby);
    }

    @DisplayName("deleteLobbyTest1")
    @Test
    public void lobbyInitTest1(){
        initLobby();
        LobbyManager.deleteLobby(lobby);
        assertTrue(LobbyManager.sessions.isEmpty());
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

}
