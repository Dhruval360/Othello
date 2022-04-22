package com.example.othello.Views;
import android.os.Bundle;
import com.example.othello.R;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Controller.Controller;

public class AImenuActivity extends AppCompatActivity{
    private final Controller controllerObj = Controller.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_menu);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide(); // Hides the top bar which displays the app's name

        if(controllerObj==null){
            System.out.println("true");
        }
        else{
            System.out.println("false");
            System.out.println(controllerObj);
        }

        Button easy = findViewById(R.id.AIeasy);
        easy.setOnClickListener(v -> {
            controllerObj.setAImode(1); // temp fix
//            controllerObj.switchView(this);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        Button medium = findViewById(R.id.AImedium);
        medium.setOnClickListener(v -> {
            controllerObj.setAImode(2); // temp fix
//            controllerObj.switchView(this);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        Button hard = findViewById(R.id.AIhard);
        hard.setOnClickListener(v -> {
            controllerObj.setAImode(3); // temp fix
//            controllerObj.switchView(this);
            Intent intent = new Intent(this, GameActivity.class);
            finish();
            startActivity(intent);
            //finish();
        });
    }

    //OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */){
       /* @Override
        public void OnBackPressed() {
            // Handle the back button event
            super.onBackPressed();
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }*/

    //requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    // The callback can be enabled or disabled here or in handleOnBackPressed()
}

