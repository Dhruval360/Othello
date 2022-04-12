package com.example.othello.Views;

import android.os.Bundle;
import com.example.othello.R;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Controller.Controller;

public class MenuActivity extends AppCompatActivity {
    private final Controller controllerObj = Controller.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide(); // Hides the top bar which displays the app's name

        if(controllerObj==null){
            System.out.println("true");
        }
        else{
            System.out.println("false");
            System.out.println(controllerObj);
        }

        // Clicking on twoPlayerButton will start the game
        Button twoPlayerButton = findViewById(R.id.twoPlayerButton);
        twoPlayerButton.setOnClickListener(v -> {
            controllerObj.setGameMode(1);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        Button stats = findViewById(R.id.statsButton);
        stats.setOnClickListener((v ->{
            controllerObj.getStatistics();
            Intent statsIntent = new Intent(this, StatsActivity.class);
            startActivity(statsIntent);
        }));

        // TODO: Implement similar onClickListeners for other buttons as well
    }
}