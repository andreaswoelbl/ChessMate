package com.game.chessmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.GameState;
import com.game.chessmate.GameFiles.Networking.NetworkManager;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        TextView endMsg = findViewById(R.id.label_end_message);
        if (ChessBoard.getInstance().getGameState() == GameState.LOOSE) {
            endMsg.setText("SORRY. \n YOU LOST. \n WANT TO TRY AGAIN?");
        } else if (ChessBoard.getInstance().getGameState() == GameState.WIN) {
            endMsg.setText("CONGRATULATIONS! \n YOU WON! \n WANNA GO AGAIN?");
        }

        String lastUsedName = CreateSession.name;

        Button playAgainButton = findViewById(R.id.button_play_again);
        playAgainButton.setOnClickListener(view -> {
            String lobbyCode = NetworkManager.createSession(lastUsedName);
            if (lobbyCode != null) {
                if (lobbyCode.length() > 0) {
                    Intent toLobbyIntent = new Intent(this, Lobby.class);
                    toLobbyIntent.putExtra("playername1", lastUsedName);
                    toLobbyIntent.putExtra("lobbycode", lobbyCode);
                    startActivity(toLobbyIntent);
                } else {
                    Toast.makeText(this, "No server available!", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(v -> {
            Intent createSessionIntent = new Intent(this, CreateSession.class);
            createSessionIntent.putExtra("name", lastUsedName);
            startActivity(createSessionIntent);
        });
    }
}
