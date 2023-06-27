package com.game.chessmate.GameFiles;

import com.game.chessmate.GameFiles.Networking.NetworkManager;

import org.junit.Before;

public class NetworkManagerTest {

    NetworkManager manager;

    @Before
    public void init(){
        manager=NetworkManager.getInstance();
    }

  /*  @Test
    public void createSessionTest() {
        manager.createSession("");
    }

    @Test
    public void joinSessionTest(){
        manager.joinSession("");
    }*/
}
