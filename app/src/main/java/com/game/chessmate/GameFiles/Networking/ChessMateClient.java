package com.game.chessmate.GameFiles.Networking;

import android.util.Log;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Client;
import com.game.chessmate.CreateSession;

import java.io.IOException;

/** The ChessMateClient class implements a Client that allows players to connect to a Server. */
public class ChessMateClient extends Thread {
    private static boolean loggedIn = false;
    private static ChessMateClient tempChessMateClient;
    // Thread-Save Singleton
    private static final class InstanceHolder {
        static final ChessMateClient INSTANCE = tempChessMateClient;
    }
    public static ChessMateClient getInstance(){
        if(loggedIn){
            return ChessMateClient.InstanceHolder.INSTANCE;
        } else {
            tempChessMateClient = new ChessMateClient();
            return tempChessMateClient;
        }
    }

    // Local Variables
    static Client clientInstance;
    static int TCP_PORT = 53216, TIMEOUT = 5000;
    public static String HOST_IP = "se2-demo.aau.at";
    private Object response;
    private static ClientListener clientListener;

    ChessMateClient(){ this.start(); }

    @Override
    public void run() {
        init();
    }

    private static void init(){
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
            loggedIn = true;
            ChessMateClient.getInstance();
        } catch (IOException e) {
            Log.e("NETWORK", "[C]>Error no server found!");
        }
    }

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
