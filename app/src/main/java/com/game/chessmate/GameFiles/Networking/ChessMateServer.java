package com.game.chessmate.GameFiles.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;

import java.io.IOException;

/**
 * The ChessMateServer class implements a Server that allows players to connect to it.
 */
public class ChessMateServer {
    // Thread-Save Singleton
    private static final class InstanceHolder {
        static final ChessMateServer INSTANCE = new ChessMateServer();
    }
    public static ChessMateServer getInstance(){ return ChessMateServer.InstanceHolder.INSTANCE; }

    // Local Variables
    private int TCP_PORT = 54555;
    private int UDP_PORT = 54777;
    private Server serverInstance;

    // Constructors
    ChessMateServer(){
        serverInstance = new Server();
    }

    // Class Methods

    /**
     * registers the Request and Response Classes to kryonet
     */
    private void registerClasses(){
        Kryo kryo = serverInstance.getKryo();
        //TODO add Requests and Responses
        kryo.register(createSessionRequest.class);
        kryo.register(createSessionResponse.class);
    }


    /**
     * starts the Server
     */
    public void start() throws IOException {
        //TODO implement specific Start function
        serverInstance.start();
        serverInstance.bind(TCP_PORT, UDP_PORT);

        serverInstance.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                /*if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest)object;
                    System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    response.text = "ResponseText";
                    connection.sendTCP(response);
                }*/
            }
        });
    }

    // Getter and Setter
    public int getTCP_PORT(){
        return TCP_PORT;
    }

    public void setTCP_PORT(int port){
        TCP_PORT = port;
    }

    public int getUDP_PORT(){
        return UDP_PORT;
    }

    public void setUDP_PORT(int port){
        UDP_PORT = port;
    }
}
