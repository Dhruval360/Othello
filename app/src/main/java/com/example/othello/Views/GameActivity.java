package com.example.othello.Views;

import android.os.Bundle;
import com.example.othello.R;
import android.widget.Button;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Controller.Controller;

public class GameActivity extends AppCompatActivity {
    private final Controller controllerObj = Controller.getInstance();
    private final Button[][] buttons = new Button[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Prevent screen rotation
        if(getSupportActionBar() != null)
            getSupportActionBar().hide(); // Hides the top bar which displays the app's name

        TextView textViewPlayer1 = findViewById(R.id.text_view_p1);
        TextView textViewPlayer2 = findViewById(R.id.text_view_p2);
        TextView textViewTurn = findViewById(R.id.text_view_turn);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
            }
        }

        controllerObj.initGame(buttons, textViewPlayer1, textViewPlayer2, textViewTurn);

        // Reset Button (Might have to remove in case of multiplayer)
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> controllerObj.resetGame());
    }
}