package com.game.chessmate.GameFiles;

import android.content.Context;
import android.widget.Toast;

public class CheatFunktion {

    public CheatFunktion(Context context) {
        this.context = context;
    }

    private static boolean cheatFunction;
    private int playerLocalWrongCheatReveal = 0;
    private int playerEnemyWrongCheatReveal = 0;
    private static Context context;

    Player playerLocal = ChessBoard.getInstance().getLocalPlayer();
    Player playerEnemy = ChessBoard.getInstance().getEnemyPlayer();
    float lightSensorEnemy = playerEnemy.getLightValue();
    float getLightSensorLocal = playerLocal.getLightValue();
    boolean localPlayerTurn = false;
    boolean enemyPlayerTurn = false;

    private boolean checkingIfItsLocalPlayerTurn() {
        GameState state = ChessBoard.getInstance().getGameState();
        if (state.equals("WAITING_FOR_PLAYER1_MOVE")) {
            localPlayerTurn = true;
        } else if (state.equals("WAITING_FOR_PLAYER2_MOVE")) {
            enemyPlayerTurn = true;
        }
        return localPlayerTurn;
    }

    public void determineCheat() {
        if (checkingIfItsLocalPlayerTurn() && playerEnemy.getWasLeganMove()) {
            playerDidNotCheat();
        }
        if (!checkingIfItsLocalPlayerTurn() && playerLocal.getWasLeganMove()) {
            playerDidNotCheat();
        }
        if (checkingIfItsLocalPlayerTurn() && !playerEnemy.getWasLeganMove()) {
            playerDidCheat();
        }
        if (!checkingIfItsLocalPlayerTurn() && !playerLocal.getWasLeganMove()) {
            playerDidCheat();
        }

    }


    public void playerDidCheat() {
        Field startPosition = ChessBoard.getInstance().getStartPossition();
        if (checkingIfItsLocalPlayerTurn()) {
            if (playerLocal.getLightValue() <= 500) {
                if (playerEnemy.getCheatOn() && !ChessBoard.getInstance().getwasMoveLegal()) {
                    ChessBoard.getInstance().getMovedPiece().move(startPosition);
                }
            } else if (playerEnemy.getLightValue() <= 500) {
                if (playerEnemy.getCheatOn() && !ChessBoard.getInstance().getwasMoveLegal())
                    ChessBoard.getInstance().getMovedPiece().move(startPosition);
            }
        }
    }

    public void playerDidNotCheat() {
        if (localPlayerTurn && playerLocal.getTimesCheatFunktionUsedWrongly() <= 3)
            if (playerEnemy.getCheatOn() && ChessBoard.getInstance().getwasMoveLegal()) {
                int timesLocal = playerLocal.getTimesCheatFunktionUsedWrongly();
                playerLocal.setTimesCheatFunktionUsedWrongly(timesLocal + 1);
            } else {
                Toast.makeText(context, "You wrongly accused the other player of cheating more than 3 times", Toast.LENGTH_SHORT).show();
            }

        if (!localPlayerTurn && playerEnemy.getTimesCheatFunktionUsedWrongly() <= 3) {
            if (playerLocal.getCheatOn() && ChessBoard.getInstance().getwasMoveLegal()) {
                int timesEnemy = playerEnemy.getTimesCheatFunktionUsedWrongly();
                playerEnemy.setTimesCheatFunktionUsedWrongly(timesEnemy + 1);
            } else {
                Toast.makeText(context, "You wrongly accused the other player of cheating more than 3 times", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public static void setCheatFunction(boolean cheatFunction) {
        CheatFunktion.cheatFunction = cheatFunction;
    }

    public static boolean isCheatFunction() {
        return cheatFunction;
    }
}