package com.example.othello.Views;
import android.os.Bundle;
import com.example.othello.R;
import android.widget.Button;
import android.content.Intent;
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
    }
}
