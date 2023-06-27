package com.game.chessmate.GameFiles.Networking;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

/** The ChessMateClient class implements a Client that allows players to connect to a Server. */
public class ChessMateClient extends Thread {
    // Thread-Save Singleton
    private static final class InstanceHolder {
        static final ChessMateClient INSTANCE = new ChessMateClient();
    }
    public static ChessMateClient getInstance(){ return ChessMateClient.InstanceHolder.INSTANCE; }

    // Local Variables
    static Client clientInstance;
    static int TCP_PORT = 53216, TIMEOUT = 5000;
    public static String HOST_IP = "se2-demo.aau.at";
    private Object response;
    private static ClientListener clientListener;

    ChessMateClient(){
        this.start();
    }

    @Override
    public void run() {
        try {
            clientInstance = new Client();
            NetObjectRegistrator.register(clientInstance.getKryo());
            new Thread(clientInstance).start();
            //clientInstance.start();
            clientInstance.connect(TIMEOUT,HOST_IP,TCP_PORT);
            clientListener = new ClientListener();
            clientInstance.addListener(clientListener);
            Log.i("NETWORK", "Client started!");
            //clientInstance.addListener(new ChessMateClient());
        } catch (IOException e) {
            Log.e("NETWORK", "[C]>Error no server found!");
        }
    }
    /*public void createSession(Object o){
        if(o instanceof createSessionRequest){
            createSessionRequest req = (createSessionRequest) o;
            Log.i("NETWORK","[C]>SessionRequest: " + req.getName());
            clientInstance.sendTCP(req);
        }
    }*/

    /*public Object receive() {
        clientInstance.addListener(new Listener(){
            public void received(Connection connection, Object object) {
                if(object instanceof createSessionResponse){
                    createSessionResponse req = (createSessionResponse) object;
                    Log.i("NETWORK","[T:"+Thread.currentThread().getName()+"] "+"[C]>SessionResponse: " + req.getLobbyCode());
                    response = req.getLobbyCode();
                }
            }
        });
        return response;
    }*/

    public String getLobbyCode(){ return (String) this.response;}

    public void setResNull(){
        response = null;
    }

    public static void close(){
        Log.i("NETWORK", "[C]>CLIENT DISCONNECTED!");
    }

    // Getter and Setter
    public int getTCP_PORT(){
        return TCP_PORT;
    }

    public void setTCP_PORT(int port){
        TCP_PORT = port;
    }

    public Client getClient(){ return clientInstance; }
}
