package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.game.chessmate.GameFiles.ChessBoard;

public class OptionsActivity extends AppCompatActivity {

    private static final String MUSIC_OFF = "Music off";
    private static final String MUSIC_ON = "Music on";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button toggleMusicButton = findViewById(R.id.toggle_music);
        ChessBoard board = ChessBoard.getInstance();

        if (board.isSoundOn()) {
            toggleMusicButton.setText(MUSIC_OFF);
        } else {
            toggleMusicButton.setText(MUSIC_ON);
        }

        Button backButton = findViewById(R.id.backButtonOptions);
        backButton.setOnClickListener(v -> {
            finish();
        });

        toggleMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSound(toggleMusicButton);
            }
        });

        Button changeNameButton = findViewById(R.id.changeName);

        changeNameButton.setOnClickListener(v -> {
            Intent changeNameIntent = new Intent(this, HomeActivity.class);
            startActivity(changeNameIntent);
        });
    }

    public void toggleSound(Button music) {
        ChessBoard board = ChessBoard.getInstance();
        if (board.isSoundOn()) {
            music.setText(MUSIC_ON);
            board.setSoundOn(false);
        } else {
            music.setText(MUSIC_OFF);
            board.setSoundOn(true);
        }
        Log.i("OptionsActivity", "switchSound: " + board.isSoundOn());
    }
}
