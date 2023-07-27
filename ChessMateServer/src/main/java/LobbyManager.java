import java.util.ArrayList;

/** Manages all existing lobbies */
public class LobbyManager {
    static ArrayList<Lobby> sessions = new ArrayList();
    static int MAX_SESSIONS = 50;

    /** Fetches a lobbyID not in use
     * @return new lobbyID
     * @throws IllegalStateException when MAX_SESSIONS limit was reached
     */
    public static int getNewFreeID(){
        for (int id = 0; id <= MAX_SESSIONS; id++)
            if(!checkForExistingLobbyID(id)) return id;
        throw new IllegalStateException("MAX_SESSIONS LIMIT REACHED");
    }

    /** Checks if a Lobby with specified ID already exists
     * @param id lobbyID to check
     * @return true ==> lobby already exists
     *         false ==> lobby doesn't exist
     */
    private static boolean checkForExistingLobbyID(int id){
        for (Lobby s : sessions)
            if(s.lobbyID==id) return true;
        return false;
    }

    /** Prints all lobby information */
    public static void printAllCurrentSession(){
        if(sessions.isEmpty()){
            Log.i("LOBBY","No lobbies currently online!");
        } else {
            Log.i("LOBBY","Online lobbies: "+sessions.size()+"/"+MAX_SESSIONS);
            for (Lobby s : sessions)
                s.printInfo();
        }
    }

    /** Gets all existing lobbies
     * @return ArrayList with all existing lobbies
     */
    public static ArrayList<Lobby> getSessions() {
        return sessions;
    }

    /**
     * Finds a lobby the given LobbyCode belongs to
     * @param lobbycode LobbyCode of the Lobby
     * @return Lobby ==> found lobby
     *         NULL ==> no lobby found
     */
    public static Lobby getSessionByLobbycode(String lobbycode){
        for (Lobby s:sessions)
            if(s.lobbycode.equals(lobbycode)) return s;
        return null;
    }

    /**
     * Finds a lobby the given LobbyID belongs to
     * @param lobbyID LobbyID of the Lobby
     * @return Lobby ==> found lobby,
     *         NULL ==> no lobby found
     */
    public static Lobby getSessionByLobbyID(int lobbyID){
        for (Lobby s:sessions)
            if(s.lobbyID==lobbyID) return s;
        return null;
    }

    public static void deleteLobby(Lobby lobby){
        if(lobby!=null){
            if(lobby.player2!=null) ObjectSender.sendBackToCodeScreen(lobby.player2.connection);
            sessions.remove(lobby);
        }
    }
}
