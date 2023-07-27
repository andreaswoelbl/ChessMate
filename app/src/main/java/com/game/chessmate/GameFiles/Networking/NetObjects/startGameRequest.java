package com.game.chessmate.GameFiles.Networking.NetObjects;

public class startGameRequest {
    String lobbycode;
    public startGameRequest(){}

    public String getLobbycode() {
        return lobbycode;
    }

    public void setLobbycode(String lobbycode) {
        this.lobbycode = lobbycode;
    }
}