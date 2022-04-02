package com.example.othello.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.othello.Controller.Controller;
import com.example.othello.R;

public class MenuActivity extends AppCompatActivity {

    private Controller controllerObj = Controller.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();


        // Clicking on twoPlayerButton will start the game
        Button twoPlayerButton = findViewById(R.id.twoPlayerButton);
        twoPlayerButton.setOnClickListener(v -> {
//            controllerObj.switchView(v);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });
    }
}