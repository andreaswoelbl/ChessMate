package com.game.chessmate.GameFiles.Networking;

import com.esotericsoftware.kryonet.Connection;

public class PlayerObject {
    String name;
    Connection connection;
    boolean color;

    public PlayerObject(Connection connection, String name){
        this.connection = connection;
        this.name = name;
    }

    @Override
    public String toString() {
        return "["+name+"]<"+connection+"> Color:"+color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
