package com.game.chessmate.GameFiles.Networking;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;
import com.game.chessmate.GameFiles.GameState;
import com.game.chessmate.GameFiles.Networking.NetObjects.FieldDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.GameDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** The NetworkManager class functions as the framework for networked interaction. */
public class NetworkManager {
    private static final class InstanceHolder {static final NetworkManager INSTANCE = new NetworkManager();}
    public static NetworkManager getInstance(){ return NetworkManager.InstanceHolder.INSTANCE; }
    public static ChessPieceColour initialColor;
    public static String currentLobbyCode;
    public static boolean excludeAfterMove;
    static ExecutorService service = Executors.newFixedThreadPool(1);

    public static String createSession(String name){
            Future<String> future = service.submit(new NetworkTasks.CreateSession(name));
        try{
            String lobbycode = future.get();
            Log.i("NETWORK","LobbyCode: "+lobbycode);
            NetworkManager.currentLobbyCode = lobbycode;
            return lobbycode;
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            //Log.e("NETWORK","Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
        return null;
    }

    public static LobbyDataObject joinSession(String lobbycode, String name) {
        Future<LobbyDataObject> future = service.submit(new NetworkTasks.JoinSession(lobbycode, name));
        try{
            LobbyDataObject lobbyDataObject = future.get();
            Log.i("NETWORK","LobbyCode: "+lobbyDataObject);
            NetworkManager.currentLobbyCode = lobbycode;
            return lobbyDataObject;
        } catch (InterruptedException | ExecutionException e){
            Log.e("NETWORK","Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
        return null;
    }

    public static void startGame(String lobbycode) {
        Future<startGameParameters> future = service.submit(new NetworkTasks.startGame(lobbycode));
        try{
            startGameParameters parameters = future.get();
            Log.i("COLOR","COLOR: "+parameters.getInitColour());
            NetworkManager.currentLobbyCode = lobbycode;
            NetworkManager.initialColor = parameters.getInitColour();
            ChessMateClient.getInstance().getClient().addListener(getGameCycleListener());
        } catch (InterruptedException | ExecutionException e){
            Log.e("NETWORK","Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
    }

    public static void sendMove(Field currentField, Field targetField){
        Log.i(TAG, "sendMove: " + "sendmove was called");

        FieldDataObject currentFieldObject = new FieldDataObject();
        currentFieldObject.setX(currentField.getFieldX());
        currentFieldObject.setY(currentField.getFieldY());

        FieldDataObject targetFieldObject = new FieldDataObject();
        targetFieldObject.setX(targetField.getFieldX());
        targetFieldObject.setY(targetField.getFieldY());

        GameDataObject gameDataObject = new GameDataObject();
        gameDataObject.setLobbyCode(NetworkManager.currentLobbyCode);
        gameDataObject.setMoved(true);
        gameDataObject.setOrigin(currentFieldObject);
        gameDataObject.setTarget(targetFieldObject);
        gameDataObject.setCheatActivated(!ChessBoard.getInstance().getwasMoveLegal());

        ChessMateClient.getInstance().getClient().sendTCP(gameDataObject);
    }

    public static Listener getGameCycleListener(){
        Listener gameCycleListener = new Listener(){
            public void received(Connection connection, Object object) {
                if(object instanceof GameDataObject){
                    GameDataObject gameDataObject = (GameDataObject)object;
                    ChessBoard.getInstance().getLocalPlayer().setTimesCheatFunktionUsedWrongly(gameDataObject.getWrongCheatRevealPlayer());
                        ChessBoard.getInstance().updateButtonText();
                    if(gameDataObject.isWin()){
                        Log.d(TAG, "received: " + gameDataObject +" "+ gameDataObject.isWin());
                        receiveWin();
                    } else if(gameDataObject.isLoose()){
                        Log.d(TAG, "received: " + gameDataObject +" "+ gameDataObject.isLoose());
                        receiveLoose();
                    } else if (gameDataObject.isMoved()) {
                        NetworkManager.excludeAfterMove = false;
                        if(gameDataObject.isMovedBack()){excludeAfterMove=true;}
                        receiveMove(gameDataObject.getOrigin(), gameDataObject.getTarget());
                    }
                }
            }
        };
        return gameCycleListener;
    }

    public static void receiveMove(FieldDataObject origin, FieldDataObject target){
        Log.i(TAG, "receiveMove: receivemove was called");
        Log.i("RECEIVE_MOVE", String.valueOf(origin));
        Log.i("RECEIVE_MOVE", String.valueOf(target));
        Field originField = ChessBoard.getInstance().getBoardFields()[origin.getX()][origin.getY()];
        Field targetField = ChessBoard.getInstance().getBoardFields()[target.getX()][target.getY()];

        Log.i("RECEIVE_MOVE", String.valueOf(originField));
        Log.i("RECEIVE_MOVE", String.valueOf(originField.getFieldX()));
        Log.i("RECEIVE_MOVE", String.valueOf(targetField.getFieldY()));
        Log.i("RECEIVE_MOVE", String.valueOf(targetField));
        Log.i("RECEIVE_MOVE", String.valueOf(targetField.getFieldX()));
        Log.i("RECEIVE_MOVE", String.valueOf(targetField.getFieldY()));
        if(originField!=null && targetField!=null ) originField.getCurrentPiece().move(targetField);
    }

    public static void receiveWin(){
        Log.d(TAG, "receiveWin: " + "was called");
        ChessBoard.getInstance().setGameState(GameState.WIN);
        ChessBoard.getInstance().redirectToEndScreen();
    }

    public static void receiveLoose(){
        Log.d(TAG, "receiveLoose: " + "was called");
        ChessBoard.getInstance().setGameState(GameState.LOOSE);
        ChessBoard.getInstance().redirectToEndScreen();
    }

    public static ChessPieceColour getInitialColor() {
        return initialColor;
    }

    public static void setInitialColor(ChessPieceColour initialColor) {
        NetworkManager.initialColor = initialColor;
    }
}
