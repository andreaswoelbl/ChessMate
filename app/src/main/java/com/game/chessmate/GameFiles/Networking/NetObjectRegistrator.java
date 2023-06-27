package com.game.chessmate.GameFiles.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.game.chessmate.GameFiles.Networking.NetObjects.GameStates;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.PlayerDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionRequest;

/**
 * registers the Request and Response Classes to kryonet
 */
public class NetObjectRegistrator {
    public static void register(Kryo k){
        k.register(createSessionRequest.class);
        k.register(createSessionResponse.class);
        k.register(joinSessionRequest.class);
        k.register(GameStates.class);
        k.register(LobbyDataObject.class);
        k.register(PlayerDataObject.class);
    }
}
