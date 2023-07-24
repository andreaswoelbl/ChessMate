package com.game.chessmate.GameFiles.Networking;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.leaveLobbyRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameRequest;

import java.util.concurrent.Callable;

public class NetworkTasks {
    public static class CreateSession implements Callable<String>{
        private final String name;
        public CreateSession(String name) {
            this.name = name;
        }
        @Override
        public String call() {
            ChessMateClient.getInstance(); //creates and starts Client
            createSessionRequest req = new createSessionRequest();
            req.setName(this.name);
            Log.i("NETWORK","[C]>SessionRequest: " + req.getName());
            ChessMateClient.getInstance().getClient().sendTCP(req);

            final String[] lobbycode = new String[1];

            Listener listener = new Listener(){
                public void received(Connection connection, Object object) {
                    if (object instanceof createSessionResponse) {
                        createSessionResponse req = (createSessionResponse) object;
                        Log.i("NETWORK", "[T:" + Thread.currentThread().getName() + "] " + "[C]>SessionResponse: " + req.getLobbyCode());
                        lobbycode[0] = req.getLobbyCode();
                    }
                    Log.i("NETWORK",connection.toString() +"\t"+ object.toString() +"\n");
                }
            };
            ChessMateClient.getInstance().getClient().addListener(listener);
            while(lobbycode[0]==null){}
            ChessMateClient.getInstance().getClient().removeListener(listener);
            return lobbycode[0];
        }
    }

    public static class JoinSession implements Callable<LobbyDataObject>{
        private final String lobbycode;
        private final String name;
        public JoinSession(String lobbycode, String name) {
            this.lobbycode = lobbycode;
            this.name = name;
        }
        @Override
        public LobbyDataObject call() {
            ChessMateClient.getInstance(); //creates and starts Client
            joinSessionRequest req = new joinSessionRequest();
            req.setLobbycode(this.lobbycode);
            req.setName(this.name);
            Log.i("NETWORK","[C]>SessionRequest: " + req.getLobbycode());
            ChessMateClient.getInstance().getClient().sendTCP(req);

            final LobbyDataObject[] lobbyDataObject = new LobbyDataObject[1];

            Listener listener = new Listener(){
                public void received(Connection connection, Object object) {
                    if (object instanceof LobbyDataObject) {
                        lobbyDataObject[0] = (LobbyDataObject) object;
                        Log.i("NETWORK", "Received: lobbyDataObject");
                    }
                    Log.i("NETWORK",connection.toString() +"\t"+ object.toString() +"\n");
                }
            };
            ChessMateClient.getInstance().getClient().addListener(listener);
            while(lobbyDataObject[0]==null){}
            ChessMateClient.getInstance().getClient().removeListener(listener);
            return lobbyDataObject[0];
        }
    }

    public static class startGame implements Callable<startGameParameters>{
        private final String lobbycode;
        public startGame(String lobbycode) {
            this.lobbycode = lobbycode;
        }
        @Override
        public startGameParameters call() {
            ChessMateClient.getInstance(); //creates and starts Client
            startGameRequest req = new startGameRequest();
            req.setLobbycode(this.lobbycode);
            Log.i("NETWORK","[C]>SessionRequest: " + req.getLobbycode());
            ChessMateClient.getInstance().getClient().sendTCP(req);

            final startGameParameters[] parameters = new startGameParameters[1];

            Listener listener = new Listener(){
                public void received(Connection connection, Object object) {
                    if (object instanceof startGameParameters) {
                        parameters[0] = (startGameParameters) object;
                        Log.i("NETWORK", "Received: startGameParameters");
                    }
                    Log.i("NETWORK",connection.toString() +"\t"+ object.toString() +"\n");
                }
            };
            ChessMateClient.getInstance().getClient().addListener(listener);
            while(parameters[0]==null){}
            ChessMateClient.getInstance().getClient().removeListener(listener);
            return parameters[0];
        }
    }

    /*public static void leaveSession() {
        ChessMateClient.getInstance(); //creates and starts Client
        leaveLobbyRequest req = new leaveLobbyRequest();
        ChessMateClient.getInstance().getClient().sendTCP(req);
    }

                String lobbycode = null;
            while(lobbycode==null){ClientListener.getLobbycode();}
            return lobbycode;
    */
}