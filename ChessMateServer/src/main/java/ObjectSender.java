import NetObjects.*;
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

    public static void sendBackToCodeScreen(Connection con){
        LobbyDataObject lobbyDataObject = new LobbyDataObject();
        lobbyDataObject.setClearLobby(true);
        System.out.println("Sending: " + lobbyDataObject);
        con.sendTCP(lobbyDataObject);
    }

    public static void sendStartGameParameters(Connection con, PlayerObject p){
        startGameParameters parameters = new startGameParameters();
        parameters.setInitColour(p.chessPieceColour);
        System.out.println("Sending: " + parameters.getInitColour().toString());
        con.sendTCP(parameters);
    }

    public static void sendGameDataObject(Connection con, Lobby lobby, GameDataObject g){
        GameDataObject gameDataObject = g;
        gameDataObject.setOrigin(Lobby.mirrorFunc(g.getOrigin()));
        gameDataObject.setTarget(Lobby.mirrorFunc(g.getTarget()));
        if(lobby.player1.connection==con) gameDataObject.setWrongCheatRevealPlayer(lobby.player1.maxWrongCheatReveal);
        if(lobby.player2.connection==con) gameDataObject.setWrongCheatRevealPlayer(lobby.player2.maxWrongCheatReveal);
        System.out.println("Sending: " + gameDataObject);
        con.sendTCP(gameDataObject);
    }

    public static void sendEndStateGameDataObject(Connection con, boolean endState){
        GameDataObject gameDataObject = new GameDataObject();
        if(endState){
            gameDataObject.setWin(true);
        } else {
            gameDataObject.setLoose(true);
        }
        System.out.println("Sending: " + gameDataObject);
        con.sendTCP(gameDataObject);
    }

    public static void sendGameDataObjectNoFlip(Connection con, Lobby lobby, GameDataObject g){
        GameDataObject gameDataObject = g;
        gameDataObject.setOrigin(g.getOrigin());
        gameDataObject.setTarget(g.getTarget());
        gameDataObject.setMovedBack(true);
        if(lobby.player1.connection==con) gameDataObject.setWrongCheatRevealPlayer(lobby.player1.maxWrongCheatReveal);
        if(lobby.player2.connection==con) gameDataObject.setWrongCheatRevealPlayer(lobby.player2.maxWrongCheatReveal);
        System.out.println("Sending: " + gameDataObject);
        con.sendTCP(gameDataObject);
    }

    public static void sendErrorPacket(Connection con, String errorMsg){
        ErrorPacket errorPacket = new ErrorPacket();
        errorPacket.setErrorMsg(errorMsg);
        Log.e("ErrorPacket","Sending: " + errorPacket);
        con.sendTCP(errorPacket);
    }
}
