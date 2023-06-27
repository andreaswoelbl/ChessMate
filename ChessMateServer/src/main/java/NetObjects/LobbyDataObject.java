package NetObjects;

public class LobbyDataObject {
    long lobbyID;
    String lobbycode;
    byte playercount;
    GameStates currentLobbyState;

    PlayerDataObject player1;
    PlayerDataObject player2;
    boolean cheatFuncActive;
    boolean clearLobby;
    public LobbyDataObject(){}

    public long getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(long lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getLobbycode() {
        return lobbycode;
    }

    public void setLobbycode(String lobbycode) {
        this.lobbycode = lobbycode;
    }

    public byte getPlayercount() {
        return playercount;
    }

    public void setPlayercount(byte playercount) {
        this.playercount = playercount;
    }

    public GameStates getCurrentLobbyState() {
        return currentLobbyState;
    }

    public void setCurrentLobbyState(GameStates currentLobbyState) {
        this.currentLobbyState = currentLobbyState;
    }

    public PlayerDataObject getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerDataObject player1) {
        this.player1 = player1;
    }

    public PlayerDataObject getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerDataObject player2) {
        this.player2 = player2;
    }

    public boolean isCheatFuncActive() {
        return cheatFuncActive;
    }

    public void setCheatFuncActive(boolean cheatFuncActive) {
        this.cheatFuncActive = cheatFuncActive;
    }

    public boolean isClearLobby() {
        return clearLobby;
    }

    public void setClearLobby(boolean clearLobby) {
        this.clearLobby = clearLobby;
    }
}
