package com.game.chessmate.GameFiles.Networking;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;

public class ClientListener extends Listener {
    public static String lobbycode = null;
    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        /*if(object instanceof createSessionResponse){
            lobbycode = null;
            createSessionResponse req = (createSessionResponse) object;
            Log.i("NETWORK", "[T:" + Thread.currentThread().getName() + "] " + "[C]>SessionResponse: " + req.getLobbyCode());
            lobbycode = req.getLobbyCode();
        }*/

    }

    public static String getLobbycode() {
        return lobbycode;
    }
}
