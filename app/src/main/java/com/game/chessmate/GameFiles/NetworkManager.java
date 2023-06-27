package com.game.chessmate.GameFiles;

import android.util.Log;

/** The NetworkManager class functions as the framework for networked interaction. */
public class NetworkManager extends Thread{
    private static final class InstanceHolder {static final NetworkManager INSTANCE = new NetworkManager();}
    public static NetworkManager getInstance(){ return NetworkManager.InstanceHolder.INSTANCE; }

    public String name;
    public boolean running;
    Thread thread;

    NetworkManager(){
        this.start();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("NETWORK_ChessMate");
        Log.i("NETWORK","[T:"+Thread.currentThread().getName()+"] "+"initializing");
    }
    /*public static CompletableFuture<String> createSession(String name) {
        return CompletableFuture.supplyAsync(() -> {
            Log.i("NETWORK","[T:"+Thread.currentThread().getName()+"] "+"creating Session");
            createSessionRequest r = new createSessionRequest();
            r.setName(name);
            ChessMateClient.createSession(r);
        });
    }

    public String createSessionFunc(String name){
        Runnable createSessionRun = () -> {
            Log.i("NETWORK","[T:"+Thread.currentThread().getName()+"] "+"creating Session");
            createSessionRequest r = new createSessionRequest();
            r.setName(name);
            ChessMateClient.createSession(r);
        };
        thread = new Thread(createSessionRun);
        Log.i("NETWORK","[T:"+Thread.currentThread().getName()+"] "+"creating Session");
        thread.start();
        while(thread.isAlive()){}
        return (String) ChessMateClient.getInstance().response;
    }*/

    /*Log.e("NETWORK","FAILED TO START SERVER");
    Log.e("NETWORK","FAILED TO START CLIENT");
    Log.i("NETWORK","CREATING SESSION...");
    Log.i("NETWORK","JOINING SESSION...");*/
}
