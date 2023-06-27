
import NetObjects.createSessionRequest;
import NetObjects.createSessionResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ChessMateServer chessMateServer = new ChessMateServer();
        chessMateServer.start();
        Scanner in = new Scanner(System.in);
        String input="";
        boolean commandExecuted;
        try {Thread.sleep(2000);} catch(InterruptedException e) {}
        System.out.println("Type '/help' to see the available commands!");
        while(true){
            commandExecuted = false;
            if(in.hasNext()){ input = in.nextLine(); }
            if(input.equalsIgnoreCase("/quit")){
                System.exit(0);
                commandExecuted = true;
            }
            if(input.equalsIgnoreCase("/help")){
                System.out.println("[HELP PAGE]");
                System.out.println(" - "+"/quit"+"\t"+"Closes the Server");
                System.out.println(" - "+"/list"+"\t"+"Lists informations about the Lobbies online");
                commandExecuted = true;
            }
            if(input.equalsIgnoreCase("/list")){
                LobbyManager.printAllCurrentSession();
                commandExecuted = true;
            }
            if(commandExecuted) input="";
        }
    }
}
