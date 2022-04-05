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

        // Clicking on twoPlayerButton will start the game
        Button twoPlayerButton = findViewById(R.id.twoPlayerButton);
        twoPlayerButton.setOnClickListener(v -> {
//            controllerObj.switchView(this);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        // TODO: Implement similar onClickListeners for other buttons as well
    }
}