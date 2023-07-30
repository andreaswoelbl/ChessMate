package com.game.chessmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        EditText enterName = findViewById(R.id.EnterName);
        TextView pleaseEnterName = findViewById(R.id.pleaseEnterYourName);

        Button enterGame = findViewById(R.id.EnterGameButton);
        enterGame.setOnClickListener(v -> {
            if (!enterName.getText().toString().matches("")) {
                Intent createSessionIntent = new Intent(this, CreateSession.class);
                createSessionIntent.putExtra("name", enterName.getText().toString());
                startActivity(createSessionIntent);
            } else {
                pleaseEnterName.setText("Please Enter Your Name!");
            }
        });

    }

}