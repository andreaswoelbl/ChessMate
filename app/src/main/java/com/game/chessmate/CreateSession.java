package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.game.chessmate.GameFiles.Networking.ChessMateClient;
import com.game.chessmate.GameFiles.Networking.NetworkManager;

public class CreateSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        getSupportActionBar().hide();

        ChessMateClient.getInstance();

        Button createSession=(Button) findViewById(R.id.createSessionButton);
        Button joinSession=(Button) findViewById(R.id.joinSessionButton);

        TextView namedisplay = findViewById(R.id.playerName);
        String name = getIntent().getExtras().getString("name");

        namedisplay.setText("Welcome "+ name);

        createSession.setOnClickListener(v -> {
            String lobbycode = NetworkManager.createSession(name);
            if(lobbycode!=null){
                Intent toLobbyIntent = new Intent(this, Lobby.class);
                toLobbyIntent.putExtra("playername1",name);
                toLobbyIntent.putExtra("lobbycode",lobbycode);
                startActivity(toLobbyIntent);
            }
        });

        joinSession.setOnClickListener(v -> {
            Intent joinSessionIntent = new Intent(this, EnterCodeActivity.class);
            joinSessionIntent.putExtra("name",name);
            startActivity(joinSessionIntent);
        });
    }
}