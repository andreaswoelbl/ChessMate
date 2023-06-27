package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.game.chessmate.GameFiles.ResourceLoader;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        ResourceLoader loader = new ResourceLoader(getResources(), width);
        loader.execute();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openNewActivity();            }
        });

        EditText enterName = findViewById(R.id.EnterName);
        String name = enterName.getText().toString();
        TextView pleaseEnterName = findViewById(R.id.pleaseEnterYourName);

        Button enterGame = findViewById(R.id.EnterGameButton);
        enterGame.setOnClickListener(v -> {
            if (!enterName.getText().toString().matches("")) {
                Intent createSessionIntent = new Intent(this, CreateSession.class);
                createSessionIntent.putExtra("name",enterName.getText().toString());
                startActivity(createSessionIntent);
            } else {
                pleaseEnterName.setText("Please Enter Your Name!");
            }
        });

    }


    public void openNewActivity() {
        new Handler().post(() -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });
    }
}