package com.example.othello.Views;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Controller.Controller;
import com.example.othello.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StatsActivity extends AppCompatActivity {
    private final Controller controllerObj = Controller.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Prevent screen rotation
        if(getSupportActionBar() != null)
            getSupportActionBar().hide(); // Hides the top bar which displays the app's name

//        controllerObj.getStatistics();

        //TODO: Make the UI for the Leaderboard

    }
}
