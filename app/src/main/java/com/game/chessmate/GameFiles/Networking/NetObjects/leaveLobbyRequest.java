package com.game.chessmate.GameFiles.Networking.NetObjects;

public class leaveLobbyRequest {
    String lobbycode;
    public leaveLobbyRequest() {}

    public String getLobbycode() {
        return lobbycode;
    }

    public void setLobbycode(String lobbycode) {
        this.lobbycode = lobbycode;
    }
}