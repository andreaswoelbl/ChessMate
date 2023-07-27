package com.game.chessmate.GameFiles.Networking.NetObjects;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

public class PlayerDataObject {
    String name;
    ChessPieceColour chessPieceColour;
    int maxWrongCheatReveal;

    public PlayerDataObject(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChessPieceColour getChessPieceColour() { return chessPieceColour; }

    public void setChessPieceColour(ChessPieceColour chessPieceColour) {
        this.chessPieceColour = chessPieceColour;
    }

    public int getMaxWrongCheatReveal() { return maxWrongCheatReveal; }

    public void setMaxWrongCheatReveal(int maxWrongCheatReveal) {
        this.maxWrongCheatReveal = maxWrongCheatReveal;
    }
}
