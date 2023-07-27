import NetObjects.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ChessMateServer extends Thread{
    int TCP_PORT = 53216;
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
            Log.accept("SERVER","Server started!");
        } catch (IOException e) {
            Log.e("SERVER","Error starting server!");
        }

        serverInstance.addListener(new Listener() {
            @Override
            public void received (Connection con, Object o) {
                if (o instanceof leaveLobbyRequest){
                    Log.accept("LEAVE_SESSION_REQUEST","Received LEAVE REQUEST");
                    leaveLobbyRequest req = (leaveLobbyRequest)o;
                    Lobby lobby = LobbyManager.getSessionByLobbycode(req.getLobbycode());
                    if(lobby!=null){
                        if(lobby.player1!=null)
                            if(con == lobby.player1.connection) lobby.player1Leave();

                        if(lobby.player2!=null)
                            if(con == lobby.player2.connection){
                                lobby.player2Leave();
                                ObjectSender.sendLobbyDataObject(lobby.player1.connection,lobby);
                            }
                    }
                }

                if (o instanceof createSessionRequest) {
                    Log.accept("CREATE_SESSION_REQUEST","Received CREATE REQUEST");
                    createSessionRequest request = (createSessionRequest)o;
                    Log.i("CREATE_SESSION_REQUEST","Lobbycode: " + request.getName());
                    Lobby lobby = new Lobby();
                    lobby.player1Join(con, request.getName());
                    LobbyManager.getSessions().add(lobby);
                    ObjectSender.createLobbyResponse(con, lobby);
                }

                if (o instanceof joinSessionRequest) {
                    Log.accept("JOIN_SESSION_REQUEST","Received JOIN REQUEST");
                    joinSessionRequest request = (joinSessionRequest)o;
                    System.out.println("Name: " + request.getName());
                    System.out.println("LobbyCode: " + request.getLobbycode());
                    Lobby lobby = LobbyManager.getSessionByLobbycode(request.getLobbycode());
                    if(lobby!=null){
                        lobby.player2Join(con,request.getName());
                        ObjectSender.sendLobbyDataObject(con,lobby);
                        ObjectSender.sendLobbyDataObject(lobby.player1.connection,lobby);
                    } else {
                        ObjectSender.sendErrorPacket(con,"Lobby doens't exist!");
                    }
                }

                if (o instanceof startGameRequest) {
                    Log.accept("START_GAME_REQUEST","Received START GAME REQUEST");
                    startGameRequest request = (startGameRequest)o;
                    Lobby lobby = LobbyManager.getSessionByLobbycode(request.getLobbycode());
                    if(lobby!=null){
                        lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER1_INPUT;
                        lobby.inGame = true;
                        ObjectSender.sendStartGameParameters(con,lobby.player1);
                        ObjectSender.sendStartGameParameters(lobby.player2.connection,lobby.player2);
                    } else {
                        ObjectSender.sendErrorPacket(con,"Error processing your gameInput!");
                    }
                }

                if(o instanceof SensorActivationObject){
                    SensorActivationObject request = (SensorActivationObject)o;
                    Lobby lobby = LobbyManager.getSessionByLobbycode(request.getLobbyCode());
                    if(lobby!=null){
                        if(lobby.canReceiveSensorPacket){
                            if(lobby.cheatFuncActive){
                                Log.accept("SENSOR_PACKET","Received SENSOR PACKET");
                                if(lobby.player1.connection == con) {
                                    // Player 1 Revealed cheat
                                    if (lobby.lastMoveOrigin != null) {
                                        FieldDataObject originField = lobby.lastMoveOrigin;
                                        FieldDataObject targetField = lobby.lastMoveTarget;
                                        GameDataObject moveBackToOrigin = new GameDataObject();
                                        moveBackToOrigin.setOrigin(targetField);
                                        moveBackToOrigin.setTarget(originField);
                                        moveBackToOrigin.setMoved(true);
                                        /* lobby.player2.maxWrongCheatReveal--;
                                        if (lobby.player2.maxWrongCheatReveal<=0){
                                         GameDataObject gameDataObject = new GameDataObject();
                                         gameDataObject.setWrongCheatRevealPlayer2(lobby.player2.maxWrongCheatReveal);
                                         gameDataObject.setWrongCheatRevealPlayer1(lobby.player1.maxWrongCheatReveal);
                                        }*/
                                        Log.i("SENSOR_PACKET","Player1 revealed the cheat");
                                        //TODO tell player 2 that his cheat was reveald
                                        lobby.player2.maxWrongCheatReveal--;
                                        ObjectSender.sendGameDataObjectNoFlip(lobby.player2.connection, lobby, moveBackToOrigin);
                                        ObjectSender.sendGameDataObject(lobby.player1.connection, lobby, moveBackToOrigin);
                                        if(lobby.player2.maxWrongCheatReveal==0){
                                            ObjectSender.sendEndStateGameDataObject(lobby.player2.connection,false);
                                            ObjectSender.sendEndStateGameDataObject(lobby.player1.connection,true);
                                        }
                                        lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER2_INPUT;
                                    }
                                }

                                if(lobby.player2.connection==con) {
                                    if (lobby.lastMoveOrigin != null) {
                                        FieldDataObject originField = lobby.lastMoveOrigin;
                                        FieldDataObject targetField = lobby.lastMoveTarget;
                                        GameDataObject moveBackToOrigin = new GameDataObject();
                                        moveBackToOrigin.setOrigin(targetField);
                                        moveBackToOrigin.setTarget(originField);
                                        moveBackToOrigin.setMoved(true);
                                        /*lobby.player1.maxWrongCheatReveal--;
                                         if (lobby.player1.maxWrongCheatReveal<=0){
                                             GameDataObject gameDataObject = new GameDataObject();

                                             ObjectSender.sendEndStateGameDataObject(con, gameDataObject,true);
                                             ObjectSender.sendEndStateGameDataObject(lobby.player1.connection, gameDataObject,false);
                                         }*/

                                        Log.i("SENSOR_PACKET", "Player2 revealed the cheat");
                                        //TODO tell player 1 that his cheat was reveald
                                        lobby.player1.maxWrongCheatReveal--;
                                        ObjectSender.sendGameDataObjectNoFlip(lobby.player1.connection, lobby, moveBackToOrigin);
                                        ObjectSender.sendGameDataObject(lobby.player2.connection, lobby, moveBackToOrigin);

                                        if(lobby.player1.maxWrongCheatReveal==0){
                                            ObjectSender.sendEndStateGameDataObject(lobby.player1.connection,false);
                                            ObjectSender.sendEndStateGameDataObject(lobby.player2.connection,true);
                                        }
                                        lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER1_INPUT;
                                    }
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.v("SENSOR_PACKET","Sensor got activated and other player cheated");
                                lobby.cheatFuncActive = false;
                                lobby.canReceiveSensorPacket = false;
                            } else {
                                Log.v("SENSOR_PACKET","Sensor got activated and other player did not cheat");
                                if(lobby.player1.connection == con) {
                                    if (lobby.player1.maxWrongCheatReveal > 0) {
                                        lobby.player1.maxWrongCheatReveal--;
                                        if(lobby.player1.maxWrongCheatReveal==0){
                                            ObjectSender.sendEndStateGameDataObject(lobby.player1.connection,false);
                                            ObjectSender.sendEndStateGameDataObject(lobby.player2.connection,true);
                                        }
                                        lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER1_INPUT;
                                        // ObjectSender.sendGameDataObject(lobby.player2.connection, lobby, request);
                                    } else {
                                        Log.i("SENSOR_PACKET","Player1 ran out of reveals!");
                                        //TODO anzeigen wieviele Aufdeckversuche noch vorhanden sind
                                        // lobby.currentLobbyState = GameStates.GAMEOVER;
                                    }
                                }

                                if(lobby.player2.connection == con){
                                    if(lobby.player2.maxWrongCheatReveal > 0){
                                        lobby.player2.maxWrongCheatReveal--;
                                        if(lobby.player2.maxWrongCheatReveal==0){
                                            ObjectSender.sendEndStateGameDataObject(lobby.player2.connection,false);
                                            ObjectSender.sendEndStateGameDataObject(lobby.player1.connection,true);
                                        }
                                        lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER2_INPUT;
                                    } else {
                                        Log.i("SENSOR_PACKET", "Player2 ran out of reveals!");
                                        //TODO anzeigen wieviele Aufdeckversuche noch vorhanden sind
                                        // lobby.currentLobbyState = GameStates.GAMEOVER;
                                    }
                                }
                                lobby.canReceiveSensorPacket = false;
                            }
                        }
                    }
                }

                if (o instanceof GameDataObject) {
                    Log.accept("GAME_DATA_OBJECT","Received Game Data");
                    GameDataObject request = (GameDataObject)o;
                    Lobby lobby = LobbyManager.getSessionByLobbycode(request.getLobbyCode());
                    if(lobby!=null){
                        if(lobby.cheatFuncActive){
                            //WHEN PLAYER PROCEEDED WITH MOVE INSTEAD OF SENSORACTIVATION
                            lobby.canReceiveSensorPacket = false;
                            lobby.cheatFuncActive = false;
                        }
                        lobby.lastMoveOrigin = request.getOrigin();
                        lobby.lastMoveTarget = request.getTarget();
                        lobby.cheatFuncActive = request.isCheatActivated();
                        Log.i("GameDataObject",request.getOrigin().toString());
                        Log.i("GameDataObject",request.getTarget().toString());
                        Log.i("GameDataObject", "MovedBackVar: "+request.isMovedBack());
                        if(lobby.currentLobbyState == GameStates.WAITING_FOR_PLAYER1_INPUT){
                            System.out.println("Current Lobbystate was WaitForPlayer1move");
                            if(con==lobby.player1.connection){
                                System.out.println("Connection was the same with player 1");
                                System.out.println(lobby.player1);
                                lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER2_INPUT;
                                ObjectSender.sendGameDataObject(lobby.player2.connection, lobby, request);
                            }
                        }
                        if(lobby.currentLobbyState == GameStates.WAITING_FOR_PLAYER2_INPUT){
                            System.out.println("Current Lobbystate was WaitForPlayer2move");
                            if(con==lobby.player2.connection){
                                System.out.println("Connection was the same with player 2");
                                System.out.println(lobby.player2);
                                lobby.currentLobbyState = GameStates.WAITING_FOR_PLAYER1_INPUT;
                                ObjectSender.sendGameDataObject(lobby.player1.connection, lobby, request);
                            }
                        }
                        lobby.canReceiveSensorPacket = true;
                    } else {
                        ObjectSender.sendErrorPacket(con,"Error processing your gameInput!");
                    }
                }
                //Log.v("SERVER",con.toString() +"\t"+ o.toString() +"\n");
            }

            @Override
            public void connected(Connection connection) {
                Log.i("SERVER",connection.toString()+" joined");
            }

            @Override
            public void disconnected(Connection connection) {
                Log.i("SERVER",connection.toString()+" left");
                System.out.println("Disconnected: " +connection.toString());
            }
        });
    }
}
