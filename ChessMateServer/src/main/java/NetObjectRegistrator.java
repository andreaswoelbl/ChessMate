import NetObjects.*;
import com.esotericsoftware.kryo.Kryo;

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
