package com.game.chessmate.GameFiles.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.game.chessmate.GameFiles.Networking.NetObjects.CardDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.ErrorPacket;
import com.game.chessmate.GameFiles.Networking.NetObjects.FieldDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.GameDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.GameStates;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.PlayerDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.SensorActivationObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.createSessionResponse;
import com.game.chessmate.GameFiles.Networking.NetObjects.joinSessionRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.leaveLobbyRequest;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameRequest;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

/**
 * registers the Request and Response Classes to kryonet
 */
public class NetObjectRegistrator {
    public static void register(Kryo k){
        k.register(createSessionRequest.class);
        k.register(createSessionResponse.class);
        k.register(joinSessionRequest.class);
        k.register(leaveLobbyRequest.class);
        k.register(GameStates.class);
        k.register(LobbyDataObject.class);
        k.register(PlayerDataObject.class);
        k.register(ChessPieceColour.class);
        k.register(FieldDataObject.class);
        k.register(GameDataObject.class);
        k.register(startGameRequest.class);
        k.register(startGameParameters.class);
        k.register(GameDataObject.class);
        k.register(SensorActivationObject.class);
        k.register(CardDataObject.class);
        k.register(ErrorPacket.class);
    }
}
