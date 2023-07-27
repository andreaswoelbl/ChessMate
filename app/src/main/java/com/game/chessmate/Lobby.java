package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.GameState;
import com.game.chessmate.GameFiles.Networking.ChessMateClient;
import com.game.chessmate.GameFiles.Networking.NetObjects.LobbyDataObject;
import com.game.chessmate.GameFiles.Networking.NetObjects.startGameParameters;
import com.game.chessmate.GameFiles.Networking.NetworkManager;
import com.game.chessmate.GameFiles.Networking.NetworkTasks;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

public class Lobby extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        getSupportActionBar().hide();
        boolean isHost = false;

        TextView codeOutput = findViewById(R.id.CodeOutput);
        //codeOutput.setVisibility(View.INVISIBLE);
        TextView player1 = findViewById(R.id.player1Name);
        String name = getIntent().getExtras().getString("playername1");
        String name2 = getIntent().getExtras().getString("playername2");
        String lobbycode = getIntent().getExtras().getString("lobbycode");
        player1.setText(name);
        codeOutput.setText(lobbycode);
        TextView player2 = findViewById(R.id.player2Name);
        if(name2 == null) player2.setText("Waiting for player2");
        player2.setText(name2);

        Button enterGameLobbyButton = findViewById(R.id.enterGameLobbyButton);
        enterGameLobbyButton.setVisibility(View.INVISIBLE);

        Listener lobbyUpdateListener = new Listener(){
            public void received(Connection connection, Object object) {
                if(object instanceof LobbyDataObject){
                    LobbyDataObject req = (LobbyDataObject) object;
                    runOnUiThread(() -> {
                        if(req.isClearLobby()){
                            Intent backToCodeInputScreen = new Intent(Lobby.this, EnterCodeActivity.class);
                            backToCodeInputScreen.putExtra("name", name2);
                            startActivity(backToCodeInputScreen);
                        } else {
                            player2.setText(req.getPlayer2().getName());
                            player2.setVisibility(View.VISIBLE);
                            enterGameLobbyButton.setVisibility(View.VISIBLE);
                        }

                    });
                }

                if(object instanceof startGameParameters){
                    runOnUiThread(() -> {
                        ChessPieceColour color = ((startGameParameters) object).getInitColour();
                        if(color!=null) {
                            NetworkManager.setInitialColor(color);
                            Log.i("COLOR", "COLORlobby: " + ((startGameParameters) object).getInitColour());
                            Intent toGameIntentPlayer2 = new Intent(Lobby.this, GameActivity.class);
                            startActivity(toGameIntentPlayer2);
                            ChessMateClient.getInstance().getClient().addListener(NetworkManager.getGameCycleListener());
                            ChessBoard.getInstance().setGameState(GameState.WAITING);
                        } else {
                            Toast.makeText(Lobby.this, "Couldn't fetch starting parameter!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };

        enterGameLobbyButton.setOnClickListener(v -> {
            runOnUiThread(() -> {
                ChessMateClient.getInstance().getClient().removeListener(lobbyUpdateListener);
                NetworkManager.startGame(lobbycode);
                Intent toGameIntentPlayer2 = new Intent(Lobby.this, GameActivity.class);
                startActivity(toGameIntentPlayer2);
                ChessBoard.getInstance().setGameState(GameState.ACTIVE);
            });
        });

        ChessMateClient.getInstance().getClient().addListener(lobbyUpdateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new NetworkTasks.SendLeaveSessionPacket();
    }
}