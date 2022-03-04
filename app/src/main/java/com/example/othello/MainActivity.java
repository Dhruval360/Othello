package com.example.othello;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final private Button[][] buttons = new Button[8][8];
    final private boolean[][] buttonSet = new boolean[8][8];
    private boolean player1Turn = true;

    // Use these to show the current scores
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Prevent screen rotation

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                buttonSet[i][j] = false; // Meaning, this button hasn't been set yet
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                    buttonSet[i][j] = false; // This button hasn't been set yet
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        final String tag = ((Button) v).getTag().toString();
        final int pos = Integer.parseInt(tag);
        if (buttonSet[pos / 10][pos % 10]) return;
        if (player1Turn) v.setBackgroundResource(R.drawable.black_circle);
        else v.setBackgroundResource(R.drawable.white_circle);
        buttonSet[pos / 10][pos % 10] = true; // This button's state has been set and cannot be changed henceforth
        player1Turn = !player1Turn;
    }
}