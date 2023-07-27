package com.game.chessmate.GameFiles.Networking.NetObjects;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

public class startGameParameters {
    ChessPieceColour initColour;

    public startGameParameters() {}

    public ChessPieceColour getInitColour() {
        return initColour;
    }

    public void setInitColour(ChessPieceColour initColour) {
        this.initColour = initColour;
    }
}
