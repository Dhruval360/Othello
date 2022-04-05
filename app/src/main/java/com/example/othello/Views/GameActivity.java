package com.example.othello.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.othello.Controller.Controller;
import com.example.othello.R;

public class GameActivity extends AppCompatActivity {
    private Controller controllerObj = Controller.getInstance();

    // Use these to show the current scores
//    private TextView textViewPlayer1;
//    private TextView textViewPlayer2;
//    private TextView textViewTurn;


    private Button[][] buttons = new Button[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Prevent screen rotation
        getSupportActionBar().hide();
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

        // Reset Button
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> {
            controllerObj.resetGame();
        });
    }
}