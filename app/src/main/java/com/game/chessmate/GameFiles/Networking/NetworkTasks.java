package com.game.chessmate.GameFiles.Networking;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.chessmate.GameFiles.Field;
import com.game.chessmate.GameFiles.Networking.NetObjects.ErrorPacket;
import com.game.chessmate.GameFiles.Networking.NetObjects.FieldDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.GameDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.SensorActivationObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.leaveLobbyRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameRequest;

import java.io.IOException;
import java.util.concurrent.Callable;

public class NetworkTasks {
    public static class CreateSession implements Callable<String>{
        private final String name;
        public CreateSession(String name) {
            this.name = name;
        }
        @Override
        public String call(){
            ChessMateClient.getInstance(); //creates and starts Client
            final String[] lobbycode = new String[1];
            lobbycode[0]=null;
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
            createSessionRequest req = new createSessionRequest();
            req.setName(this.name);
            Log.i("NETWORK","[C]>SessionRequest: " + req.getName());
            ChessMateClient.getInstance().getClient().sendTCP(req);
            int errorTimeout=0;
            while(lobbycode[0]==null){
                Log.d("NETWORK", "Lobbycode" + lobbycode[0]);
                if(errorTimeout>5000){
                    lobbycode[0]="";
                }
                errorTimeout++;
            }
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
            final LobbyDataObject[] lobbyDataObject = new LobbyDataObject[1];
            lobbyDataObject[0]=null;
            Listener listener = new Listener(){
                public void received(Connection connection, Object object) {
                    if (object instanceof LobbyDataObject) {
                        lobbyDataObject[0] = (LobbyDataObject) object;
                        Log.i("NETWORK", Thread.currentThread().getName()+"Received: lobbyDataObject");
                    }

                    if(object instanceof ErrorPacket){
                        ErrorPacket errorPacket = (ErrorPacket) object;
                        LobbyDataObject tempObject = new LobbyDataObject();
                        tempObject.setClearLobby(true);
                        tempObject.setLobbycode(errorPacket.getErrorMsg());
                        lobbyDataObject[0] = tempObject;
                        Log.i("NETWORK", Thread.currentThread().getName()+"Received: ErrorPacket");
                    }
                }
            };
            ChessMateClient.getInstance().getClient().addListener(listener);
            joinSessionRequest req = new joinSessionRequest();
            req.setLobbycode(this.lobbycode);
            req.setName(this.name);
            Log.i("NETWORK","[C]>SessionRequest: " + req.getLobbycode());
            ChessMateClient.getInstance().getClient().sendTCP(req);
            int errorTimeout=0;
            while(lobbyDataObject[0]==null){
                Log.d("NETWORK", String.valueOf(lobbyDataObject[0]));
                if(errorTimeout>5000){
                    LobbyDataObject tempObject = new LobbyDataObject();
                    tempObject.setClearLobby(true);
                    tempObject.setLobbycode("No server available!");
                    lobbyDataObject[0] = tempObject;
                }
                errorTimeout++;
            }
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
            int errorTimeout=0;
            while(parameters[0]==null){
                Log.d("NETWORK", "parameters" + parameters[0]);
                if(errorTimeout>5000){
                    startGameParameters tempObject = new startGameParameters();
                    tempObject.setInitColour(null);
                    parameters[0] = tempObject;
                }
                errorTimeout++;
            }
            ChessMateClient.getInstance().getClient().removeListener(listener);
            return parameters[0];
        }
    }

    public static class SendSensorPacket extends Thread{
        public SendSensorPacket() {
            this.start();
        }
        @Override
        public void run() {
            SensorActivationObject sensorActivationObject = new SensorActivationObject();
            sensorActivationObject.setLobbyCode(NetworkManager.currentLobbyCode);
            ChessMateClient.getInstance().getClient().sendTCP(sensorActivationObject);
        }
    }

    public static class SendLeaveSessionPacket extends Thread{
        public SendLeaveSessionPacket() {
            this.start();
        }
        @Override
        public void run() {
            leaveLobbyRequest leaveLobbyRequest = new leaveLobbyRequest();
            leaveLobbyRequest.setLobbycode(NetworkManager.currentLobbyCode);
            ChessMateClient.getInstance().getClient().sendTCP(leaveLobbyRequest);
        }
    }

    public static class SendWin extends Thread{
        public SendWin(){
            this.start();
        }
        @Override
        public void run(){
            FieldDataObject itsATrap = new FieldDataObject();
            itsATrap.setX(1);
            itsATrap.setY(1);
            GameDataObject object = new GameDataObject();
            object.setOrigin(itsATrap);
            object.setTarget(itsATrap);
            object.setWin(true);
            object.setLobbyCode(NetworkManager.currentLobbyCode);
            ChessMateClient.getInstance().getClient().sendTCP(object);
        }
    }

    public static class SendCard extends Thread {
        int id;
        Field field1;
        Field field2;

        public SendCard(int id, Field field1, Field field2) {
            this.id=id;
            this.field1=field1;
            this.field2=field2;
            this.start();
        }

        @Override
        public void run() {
            FieldDataObject tempField1 = new FieldDataObject();
            tempField1.setX(field1.getFieldX());
            tempField1.setY(field1.getFieldY());

            FieldDataObject tempField2=new FieldDataObject();
            tempField2.setX(field2.getFieldX());
            tempField2.setY(field2.getFieldY());

            GameDataObject object = new GameDataObject();
            object.setCardId(id);
            object.setOrigin(tempField1);
            object.setTarget(tempField2);
            object.setUsedCard(true);
            object.setLobbyCode(NetworkManager.currentLobbyCode);
            ChessMateClient.getInstance().getClient().sendTCP(object);
        }
    }
}