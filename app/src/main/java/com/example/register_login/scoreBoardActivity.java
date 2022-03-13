package com.example.register_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scoreBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView firstScore;
    private TextView secondScore;
    private TextView thirdScore;
    private TextView finalScore;

    private Button tryAgainBtn;
    private Button exitBtn;

    private int finalScoreInt, firstInt, secondInt, thirdInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Similar to LoginTrue.java I followed this video https://www.youtube.com/watch?v=_cV7cgQFDo0
        *  to get this to work properly.*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        finalScoreInt = preferences.getInt("finalScoreInt", 0);
        firstInt = preferences.getInt("firstInt", 0);
        secondInt = preferences.getInt("secondInt", 0);
        thirdInt = preferences.getInt("thirdInt", 0);

        firstScore = findViewById(R.id.firstScore);
        secondScore = findViewById(R.id.secondScore);
        thirdScore = findViewById(R.id.thirdScore);
        finalScore = findViewById(R.id.finalScore);

        if(finalScoreInt > thirdInt){
            thirdInt = finalScoreInt;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("thirdInt", thirdInt);
            editor.apply();
        }
        if(finalScoreInt > secondInt){
            int x = secondInt;

            secondInt = finalScoreInt;
            thirdInt = x;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("thirdInt", thirdInt);
            editor.putInt("secondInt", secondInt);
            editor.apply();
        }
        if(finalScoreInt > firstInt){
            int x = firstInt;
            //int y = secondInt;

            firstInt = finalScoreInt;
            secondInt = x;
            //thirdInt = y;

            SharedPreferences.Editor editor = preferences.edit();
            //editor.putInt("thirdInt", thirdInt);
            editor.putInt("secondInt", secondInt);
            editor.putInt("firstInt", firstInt);
            editor.apply();
        }

        firstScore.setText(getText(R.string.firstPlace) + " " + firstInt);
        secondScore.setText(getText(R.string.secondPlace) + " " + secondInt);
        thirdScore.setText(getText(R.string.thirdPlace) + " " + thirdInt);

        finalScore.setText(getText(R.string.finalScore) + " " + finalScoreInt);

        tryAgainBtn = findViewById(R.id.tryAgainBtn);
        exitBtn = findViewById(R.id.exitBtn);

        tryAgainBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent tryAgainIntent = new Intent(this, LoginTrue.class);
        Intent exitIntent = new Intent(this, MainActivity.class);

        switch(view.getId()){
            case R.id.tryAgainBtn:
                startActivity(tryAgainIntent);
                break;

            case R.id.exitBtn:
                startActivity(exitIntent);
                break;

        }
    }
}