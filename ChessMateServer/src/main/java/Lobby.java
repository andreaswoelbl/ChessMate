import NetObjects.*;
import com.esotericsoftware.kryonet.Connection;

import java.util.Random;

public class Lobby {
    // GENERIC INFO
    boolean clearLobby;
    boolean inGame; // false => in lobby; true ==> inGame;
    int lobbyID;
    String lobbycode;
    byte playercount;
    GameStates currentLobbyState;

    // PLAYER INFO
    PlayerObject player1;
    PlayerObject player2;

    // CHEATFUNCTION
    boolean cheatFuncActive;
    boolean canReceiveSensorPacket;

    // HISTORY INFO
    FieldDataObject lastMoveOrigin;
    FieldDataObject lastMoveTarget;

    Lobby(){
        currentLobbyState = GameStates.INITIALIZING;
        clearLobby = false;
        player1 = null;
        player2 = null;
        playercount = 0;
        canReceiveSensorPacket=false;
        this.lobbyID = LobbyManager.getNewFreeID();
        this.lobbycode = generateLobbyCode();
        currentLobbyState = GameStates.WAITING_FOR_PLAYER;
    }

    public String generateLobbyCode(){
        Random ran = new Random(System.currentTimeMillis());
        return Integer.toString(ran.nextInt(99999)+100000);
    }

    // Player 1
    public void player1Join(Connection con, String name){
        if(this.player1==null){
            player1 = new PlayerObject(con, name, ChessPieceColour.WHITE);
            playercount++;
            updateLobby();
        }
    }

    public void player1Leave(){
        if(player1!=null){
            player1 = null;
            playercount--;
            updateLobby();
            LobbyManager.deleteLobby(this);
        }
    }
    // Player 2
    public void player2Join(Connection con, String name){
        if(this.player2==null){
            player2 = new PlayerObject(con, name, ChessPieceColour.BLACK);
            playercount++;
            updateLobby();
        }
    }

    public void player2Leave(){
        if(player2!=null){
            player2 = null;
            playercount--;
            updateLobby();
        }
    }

    public LobbyDataObject retrieveLobbyDataObject(){
        LobbyDataObject lobbyDataObject = new LobbyDataObject();
        lobbyDataObject.setLobbyID(this.lobbyID);
        lobbyDataObject.setLobbycode(this.lobbycode);

        PlayerDataObject p1 = new PlayerDataObject();
        if(this.player1!=null){
            p1.setName(this.player1.getName());
            p1.setChessPieceColour(this.player1.getChessPieceColour());
            p1.setMaxWrongCheatReveal(this.player1.getMaxWrongCheatReveal());
        } else {
            p1.setName("");
        }
        lobbyDataObject.setPlayer1(p1);

        PlayerDataObject p2 = new PlayerDataObject();
        if(this.player2!=null){
            p2.setName(this.player2.getName());
            p2.setChessPieceColour(this.player2.getChessPieceColour());
            p2.setMaxWrongCheatReveal(this.player2.getMaxWrongCheatReveal());
        } else {
            p2.setName("");
        }
        lobbyDataObject.setPlayer2(p2);

        lobbyDataObject.setPlayercount(this.playercount);
        lobbyDataObject.setCurrentLobbyState(this.currentLobbyState);
        return lobbyDataObject;
    }

    public String getLobbycode() {
        return lobbycode;
    }

    public void printInfo() {
        Log.i("LOBBY"+lobbyID,"LobbyID="+"\t"+lobbyID);
        Log.i("LOBBY"+lobbyID,"LobbyCODE="+"\t"+lobbycode);
        Log.i("LOBBY"+lobbyID,"STATE="+"\t"+this.currentLobbyState);
        Log.i("LOBBY"+lobbyID,"PlayersCOUNT="+"\t"+playercount);
        Log.i("LOBBY"+lobbyID,"Player1="+"\t"+player1);
        Log.i("LOBBY"+lobbyID,"Player2="+"\t"+player2);
        Log.i("LOBBY"+lobbyID,"cheatFunctionActive="+"\t"+cheatFuncActive);
    }

    public void updateLobby(){
        if(playercount==0){ LobbyManager.deleteLobby(this); } // removeLobbyIfEmpty
        if(playercount==2){ currentLobbyState = GameStates.READY;}
    }

    public static FieldDataObject mirrorFunc(FieldDataObject field){
        FieldDataObject mirroredField = new FieldDataObject();

        int[][] board = new int[8][8];
        int[][] invertedBoard = new int[8][8];
        board[field.getX()][field.getY()] = 1;
        int k = -1;
        for (int i = board.length-1; i >= 0; i--) {
            int h = -1;
            k++;
            for (int j = board[0].length-1; j >= 0; j--) {
                h++;
                invertedBoard[k][h] = board[i][j];
            }
        }

        int resultX = 0;
        int resultY = 0;
        for (int i = 0; i < invertedBoard.length; i++) {
            for (int j = 0; j < invertedBoard[0].length; j++) {
                if (invertedBoard[i][j] == 1) {
                    resultX = i;
                    resultY = j;
                }
            }
        }

        mirroredField.setX(resultX);
        mirroredField.setY(resultY);
        return mirroredField;
    }
}
