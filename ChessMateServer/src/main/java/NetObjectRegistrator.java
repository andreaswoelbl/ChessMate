import NetObjects.*;
import com.esotericsoftware.kryo.Kryo;

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
