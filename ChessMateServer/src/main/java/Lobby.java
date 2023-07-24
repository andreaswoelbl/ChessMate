import NetObjects.*;

import com.esotericsoftware.kryonet.Connection;

import java.util.Random;

public class Lobby {
    long lobbyID;
    String lobbycode;
    byte playercount;
    GameStates currentLobbyState;

    PlayerObject player1;
    PlayerObject player2;
    boolean cheatFuncActive;
    boolean clearLobby;
    //TODO moveList

    Lobby() {
        currentLobbyState = GameStates.INITIALIZING;
        clearLobby = false;
        player1 = null;
        player2 = null;
        playercount = 0;
        this.lobbyID = LobbyManager.getNewFreeID();
        this.lobbycode = generateLobbyCode();
        currentLobbyState = GameStates.WAITING_FOR_PLAYER;
    }

    public String generateLobbyCode() {
        Random ran = new Random(System.currentTimeMillis());
        return Integer.toString(ran.nextInt(99999) + 100000);
    }

    // Player 1
    public void _player1_join(Connection con, String name) {
        if (this.player1 == null) {
            player1 = new PlayerObject(con, name, ChessPieceColour.WHITE);
            playercount++;
            updateLobby();
        }
    }

    public void _player1_leave() {
        if (player1 != null) {
            player1 = null;
            playercount--;
            updateLobby();
        }
    }

    // Player 2
    public void _player2_join(Connection con, String name) {
        if (this.player2 == null) {
            player2 = new PlayerObject(con, name, ChessPieceColour.BLACK);
            playercount++;
            updateLobby();
        }
    }

    public void _player2_leave() {
        if (player2 != null) {
            player2 = null;
            playercount--;
            updateLobby();
        }
    }

    public LobbyDataObject retrieveLobbyDataObject() {
        LobbyDataObject lobbyDataObject = new LobbyDataObject();
        lobbyDataObject.setLobbyID(this.lobbyID);
        lobbyDataObject.setLobbycode(this.lobbycode);

        PlayerDataObject p1 = new PlayerDataObject();
        p1.setName(this.player1.getName());
        lobbyDataObject.setPlayer1(p1);

        PlayerDataObject p2 = new PlayerDataObject();
        p2.setName(this.player2.getName());
        lobbyDataObject.setPlayer2(p2);

        lobbyDataObject.setPlayercount(this.playercount);
        lobbyDataObject.setCurrentLobbyState(this.currentLobbyState);
        return lobbyDataObject;
    }

    public String getLobbycode() {
        return lobbycode;
    }

    @Override
    public String toString() {
        return "[" + lobbyID + "]<" + playercount + "> code=" + lobbycode + "\n"
                + "\t" + "state=" + currentLobbyState + "\n"
                + "\t" + "player1=" + player1 + "\n"
                + "\t" + "player2=" + player2 + "\n"
                + "\t" + "cheatFuncActive=" + cheatFuncActive;
    }

    public void updateLobby() {
        if (playercount == 0) {
            this.clearLobby = true;
        } // removeLobbyIfEmpty
        if (playercount == 2) {
            currentLobbyState = GameStates.READY;
        } // lobby can be started
    }

    public static FieldDataObject mirrorFunc(FieldDataObject field) {
        FieldDataObject mirroredField = new FieldDataObject();
        //TODO implement mirror function with standard ChessBoard size 8x8
        mirroredField.setX(123);
        mirroredField.setX(123);
        mirroredField.getX();
        mirroredField.getX();
        return field;
    }
}