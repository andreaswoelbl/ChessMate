package com.game.chessmate.GameFiles.Networking;

import android.util.Log;

import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The NetworkManager class functions as the framework for networked interaction.
 */
public class NetworkManager {
    private static final class InstanceHolder {
        static final NetworkManager INSTANCE = new NetworkManager();
    }

    public static NetworkManager getInstance() {
        return NetworkManager.InstanceHolder.INSTANCE;
    }

    public static ChessPieceColour initialColor;
    static ExecutorService service = Executors.newFixedThreadPool(10);

    public static String createSession(String name) {
        Future<String> future = service.submit(new NetworkTasks.CreateSession(name));
        try {
            String lobbycode = future.get();
            Log.i("NETWORK", "LobbyCode: " + lobbycode);

            return lobbycode;
        } catch (InterruptedException | ExecutionException e) {
            Log.e("NETWORK", "Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
        return null;
    }

    public static LobbyDataObject joinSession(String lobbycode, String name) {
        Future<LobbyDataObject> future = service.submit(new NetworkTasks.JoinSession(lobbycode, name));
        try {
            LobbyDataObject lobbyDataObject = future.get();
            Log.i("NETWORK", "LobbyCode: " + lobbyDataObject);

            return lobbyDataObject;
        } catch (InterruptedException | ExecutionException e) {
            Log.e("NETWORK", "Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
        return null;
    }

    public static void startGame(String lobbycode) {
        Future<startGameParameters> future = service.submit(new NetworkTasks.startGame(lobbycode));
        try {
            startGameParameters parameters = future.get();
            Log.i("COLOR", "COLOR: " + parameters.getInitColour());
            NetworkManager.initialColor = parameters.getInitColour();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("NETWORK", "Couldnt get Value from Future");
            //Thread.currentThread().interrupt();
        }
    }

    public static ChessPieceColour getInitialColor() {
        return initialColor;
    }

    public static void setInitialColor(ChessPieceColour initialColor) {
        NetworkManager.initialColor = initialColor;
    }

    /*public static void leaveSession() {
        NetworkTasks.leaveSession();
    }
    public static void startGame() {
    }*/
}
