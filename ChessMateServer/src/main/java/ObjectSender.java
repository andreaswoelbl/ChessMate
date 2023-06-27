import NetObjects.LobbyDataObject;
import NetObjects.createSessionResponse;
import com.esotericsoftware.kryonet.Connection;

public class ObjectSender {
    public static void createLobbyResponse(Connection con, Lobby lobby){
        createSessionResponse response = new createSessionResponse();
        response.setLobbyCode(lobby.getLobbycode());
        System.out.println("Sending: " + response.getLobbyCode());
        con.sendTCP(response);
    }
    public static void sendLobbyDataObject(Connection con, Lobby lobby){
        LobbyDataObject lobbyDataObject = lobby.retrieveLobbyDataObject();
        System.out.println("Sending: " + lobbyDataObject);
        con.sendTCP(lobbyDataObject);
    }
}
