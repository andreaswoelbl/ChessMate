package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        getSupportActionBar().hide();


        TextView codeOutput = findViewById(R.id.CodeOutput);
        //codeOutput.setVisibility(View.INVISIBLE);


        TextView player1 = findViewById(R.id.player1Name);
        String name = getIntent().getExtras().getString("playername1");
        String name2 = getIntent().getExtras().getString("playername2");
        String lobbycode =getIntent().getExtras().getString("lobbycode");
        player1.setText(name);
        codeOutput.setText(lobbycode);
        TextView player2 = findViewById(R.id.player2Name);
        player2.setText(name2);
        //player2.setVisibility(View.INVISIBLE);

        Button enterGameLobbyButton  = findViewById(R.id.enterGameLobbyButton);

        enterGameLobbyButton.setOnClickListener(v -> {
            Intent createSessionIntent=new Intent(this,GameActivity.class);
            startActivity(createSessionIntent);
        });


    }
}