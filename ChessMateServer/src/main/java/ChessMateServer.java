import NetObjects.createSessionRequest;
import NetObjects.createSessionResponse;
import NetObjects.joinSessionRequest;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ChessMateServer extends Thread{
    int TCP_PORT = 53216;//54555
    Server serverInstance;

    ChessMateServer(){}

    @Override
    public void run() {
        start();
    }

    public void start(){
        serverInstance = new Server();
        NetObjectRegistrator.register(serverInstance.getKryo());
        try {
            serverInstance.bind(TCP_PORT);
            new Thread(serverInstance).start();
        } catch (IOException e) {
            System.err.println("[S]>Error starting server!");
        }

        serverInstance.addListener(new Listener() {
            @Override
            public void received (Connection con, Object o) {
                if (o instanceof createSessionRequest) {
                    System.out.println("[CREATE_SESSION_REQUEST]");
                    // Receive
                    createSessionRequest request = (createSessionRequest)o;
                    System.out.println("Request Arg: " + request.getName());
                    // Process
                    Lobby lobby = new Lobby();
                    lobby._player1_join(con, request.getName());
                    LobbyManager.getSessions().add(lobby);
                    // Send
                    ObjectSender.createLobbyResponse(con, lobby);
                }

                if (o instanceof joinSessionRequest) {
                    System.out.println("[JOIN_SESSION_REQUEST]");
                    // Receive
                    joinSessionRequest request = (joinSessionRequest)o;
                    System.out.println("Name: " + request.getName());
                    System.out.println("LobbyCode: " + request.getLobbycode());
                    // Process
                    Lobby lobby = LobbyManager.getSessionByLobbycode(request.getLobbycode());
                    if(lobby!=null){
                        lobby._player2_join(con,request.getName());
                        // Send
                        ObjectSender.sendLobbyDataObject(con,lobby);
                    } else {
                        //TODO lobby doesn't exist
                    }
                }
                System.out.println(con.toString() +"\t"+ o.toString() +"\n");
            }

            @Override
            public void connected(Connection connection) {
                System.out.println("Connected: " +connection.toString());
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected: " +connection.toString());
            }
        });
    }
}
