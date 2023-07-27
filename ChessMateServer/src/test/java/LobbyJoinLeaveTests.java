import NetObjects.ChessPieceColour;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class LobbyJoinLeaveTests {
    Lobby lobby;
    PlayerObject player1;
    PlayerObject player2;

    public void initLobby(){
        lobby = new Lobby();
        player1 = new PlayerObject(null, "testName", ChessPieceColour.WHITE);
        player2 = new PlayerObject(null, "testName", ChessPieceColour.BLACK);
    }

    @DisplayName("Player1DoubleLeaveTest")
    @Test
    public void player1DoubleLeaveTest(){
        initLobby();
        lobby.player1Leave();
        lobby.player1Leave();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

    @DisplayName("Player2DoubleLeaveTest")
    @Test
    public void player2DoubleLeaveTest(){
        initLobby();
        lobby.player2Leave();
        lobby.player2Leave();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

    @DisplayName("TwoPlayerTwoLeaveTest")
    @Test
    public void twoPlayerTwoLeaveTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player2Join(null, "testName");
        lobby.player2Leave();
        lobby.player1Leave();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

    @DisplayName("Player1Join_Test")
    @Test
    public void player1JoinTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        assertEquals(1,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertNull(lobby.player2);
    }

    @DisplayName("Player1DoubleJoin_Test")
    @Test
    public void player1DoubleJoinTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player1Join(null, "testName");
        assertEquals(1,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertNull(lobby.player2);
    }

    @DisplayName("Player2DoubleJoin_Test")
    @Test
    public void player2DoubleJoinTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player1Join(null, "testName");
        assertEquals(1,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertNull(lobby.player2);
    }

    @DisplayName("Player2Join_Test")
    @Test
    public void player2JoinTest(){
        initLobby();
        lobby.player2Join(null, "testName");
        assertEquals(1,lobby.playercount);
        assertNull(lobby.player1);
        assertEquals(player2.getClass(),lobby.player2.getClass());
    }

    @DisplayName("Player1Leave_Test")
    @Test
    public void player1LeaveTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player1Leave();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);

    }

    @DisplayName("Player2Leave_Test")
    @Test
    public void player2LeaveTest(){
        initLobby();
        lobby.player2Join(null, "testName");
        lobby.player2Leave();
        assertEquals(0,lobby.playercount);
        assertNull(lobby.player1);
        assertNull(lobby.player2);
    }

    @DisplayName("TwoPlayerJoinTest")
    @Test
    public void twoPlayerJoinTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player2Join(null, "testName");
        assertEquals(2,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertEquals(player2.getClass(),lobby.player2.getClass());
    }

    @DisplayName("TwoPlayerOneLeaveTest")
    @Test
    public void twoPlayerOneLeaveTest(){
        initLobby();
        lobby.player1Join(null, "testName");
        lobby.player2Join(null, "testName");
        assertEquals(2,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertEquals(player2.getClass(),lobby.player2.getClass());
        lobby.player2Leave();
        assertEquals(1,lobby.playercount);
        assertEquals(player1.getClass(),lobby.player1.getClass());
        assertNull(lobby.player2);
    }
}
