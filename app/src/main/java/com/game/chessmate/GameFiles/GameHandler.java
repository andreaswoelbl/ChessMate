package com.game.chessmate.GameFiles;
/** The GameHandler class handles gameRelated events and methodcalls
 */
public class GameHandler {
    // Thread-Save Singleton
    private static final class InstanceHolder { static final GameHandler INSTANCE = new GameHandler(); }
    public static GameHandler getInstance(){ return GameHandler.InstanceHolder.INSTANCE; }
    //TODO gameHandler, implement a state machine that supports different Game States

    // Local Variables

    // Constructors
    private GameHandler(){
        //TODO create initializer for the state machine
    }

    // Class Methods
    //TODO implement methods for the State machine

    // Getter and Setter

}
