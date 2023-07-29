package com.game.chessmate;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.game.chessmate.GameFiles.CheatFunktion;
import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.GameState;
import com.game.chessmate.GameFiles.Player;

/**
 * The type Game activity.
 */
public class GameActivity extends AppCompatActivity {

    /**
     * The Exact view.
     */
    ImageView exactView;
    /**
     * The Button.
     */
    Button button;

    /**
     * The Id.
     */
    int id = 0;

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEventListener lightEventListener;
    private float maxValue;

    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Button cheatButton = getCheatButton();
        player = ChessBoard.getInstance().getLocalPlayer();

        if (sensor == null) {
            Toast.makeText(this, "Your device has no light sensor, so you won't be able to use the cheat function", Toast.LENGTH_SHORT).show();
            CheatFunktion.setCheatFunction(false);
        } else {
            CheatFunktion.setCheatFunction(true);
        }
        maxValue = sensor.getMaximumRange();
        CheatFunktion cheatFunktion = new CheatFunktion(GameActivity.this);
        //Log.d("Sensor", String.valueOf(maxValue));
        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                player.setLightValue(sensorEvent.values[0]);
                //float closeSensor = maxValue/100;
                if (sensorEvent.values[0] <= 500 && cheatButtonStatus()) {
                    cheatFunktion.determineCheat();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cheatButton.getText().toString().matches("Cheat Off")) {
                    cheatButton.setText("Cheat On");
                    player.setCheatOn(true);
                    isCheatOn = true;
                    cheatButton.setBackgroundColor(getResources().getColor(R.color.purple_200));

                } else if (cheatButton.getText().toString().matches("Cheat On")) {
                    cheatButton.setText("Cheat Off");
                    isCheatOn = false;
                    ChessBoard.getInstance().resetLegalMoves();
                    player.setCheatOn(false);
                    cheatButton.setBackgroundColor(getResources().getColor(R.color.black));

                }
            }
        });

        button = (Button) findViewById(R.id.back);


        //close card
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exactView.setVisibility(View.GONE);
                id = 0;
            }
        });
    }

    /**
     * Sets game state view.
     *
     * @param gameState the game state
     */
    public void setGameStateView(GameState gameState) {
        TextView gameStateView = findViewById(R.id.gameStateView);
        if (gameStateView != null) {
            Log.i("TAG", "setGameStateView: " + gameStateView);
            switch (gameState) {
                case ACTIVE:
                    gameStateView.setText("Your Turn !");
                    break;
                case WAITING:
                    gameStateView.setText("Waiting for enemy...");
                    break;
                case WIN:
                    gameStateView.setText("You Won !");
                    break;
                case LOOSE:
                    gameStateView.setText("You Lost");
                    break;
                default:
                    gameStateView.setText("...");
                    break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * Gets cheat button.
     *
     * @return the cheat button
     */
    public Button getCheatButton() {
        Button cheatButton = findViewById(R.id.cheatButton);
        return cheatButton;
    }

    public void updateButtonTextLocal(){
        /**
         * The Cheat button.
         */
        Button cheatButton = findViewById(R.id.cheatButton);
        if (cheatButton != null) {
            if(isCheatOn) cheatButton.setText("Cheat ON " + player.getTimesCheatFunktionUsedWrongly() + " left");
            if(!isCheatOn) cheatButton.setText("Cheat OFF " + player.getTimesCheatFunktionUsedWrongly() + " left");
        }
    }

    private static boolean isCheatOn = false;


    /**
     * Cheat button status boolean.
     *
     * @return the boolean
     */
    public static boolean cheatButtonStatus() {
        return isCheatOn;
    }
}

