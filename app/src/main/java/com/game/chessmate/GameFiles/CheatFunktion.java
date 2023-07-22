package com.game.chessmate.GameFiles;
import com.game.chessmate.GameActivity;
import com.game.chessmate.GameFiles.CheatFunktion;
import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Networking.NetObjects.PlayerDataObject;

public class CheatFunktion {
    private static boolean cheatFunction;
    private int playerWhiteWrongCheatReveal = 0;
    private int playerBlackWrongCheatReveal = 0;

    Player playerWhite = ChessBoard.getInstance().getPlayer1();
    Player playerBlack = ChessBoard.getInstance().getPlayer2();


    public  void playerDidCheat() {
        Field startPosition = ChessBoard.getInstance().getStartPossition();
        if (playerBlack.getCheatOn() && !ChessBoard.getInstance().getwasMoveLegal()) {
            ChessBoard.getInstance().getMovedPiece().move(startPosition);
        } else if (playerWhite.getCheatOn() && !ChessBoard.getInstance().getwasMoveLegal())
            ChessBoard.getInstance().getMovedPiece().move(startPosition);
    }

    public  void playerDidNotCheat(){

    }

    public static void setCheatFunction(boolean cheatFunction) {
        CheatFunktion.cheatFunction = cheatFunction;
    }
    public static boolean isCheatFunction() {
        return cheatFunction;
    }
}
}
