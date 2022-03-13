package com.example.register_login;

import static com.example.register_login.R.layout.activity_login_true;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginTrue extends AppCompatActivity implements View.OnClickListener {
    //For a majority of this section I followed along with this video
    //https://www.youtube.com/watch?v=PR2diwRvz8U
    //So there are some similarities, although I modified it to work here and how I wanted my app to look
    private QuestionClass[] questionBank = new QuestionClass[]{
            new QuestionClass(R.string.Question1, 0, R.string.trueText, R.string.falseText, R.string.FILLER, R.string.FILLER, true, R.drawable.tomatoes),
            new QuestionClass(R.string.Question2, 2, R.string.Question2a, R.string.Question2b, R.string.Question2c, R.string.Question2d, false, R.drawable.skillcape),
            new QuestionClass(R.string.Question3, 3, R.string.Question3a, R.string.Question3b, R.string.Question3c, R.string.Question3d, false, R.drawable.linux),
            new QuestionClass(R.string.Question4, 1, R.string.Question4a, R.string.Question4b, R.string.Question4c, R.string.Question4d, false, R.drawable.pokemon),
            new QuestionClass(R.string.Question5, 0, R.string.Question5a, R.string.Question5b, R.string.Question5c, R.string.Question5d, false, R.drawable.stand),
            new QuestionClass(R.string.Question6, 0, R.string.Question6a, R.string.Question6b, R.string.Question6c, R.string.Question6d, true, R.drawable.yesoryes)
    };
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView diagText;
    private Button diagButton;

    private Button responseA;
    private Button responseB;
    private Button responseC;
    private Button responseD;

    private TextView questionText;
    private TextView currentScoretxt;

    private TextView choiceA;
    private TextView choiceB;
    private TextView choiceC;
    private TextView choiceD;

    private ImageView questionImg;

    private int currentQuestion = 0;
    private int currentScore = 0;

    private boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_true);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        responseA = findViewById(R.id.answerA);
        responseB = findViewById(R.id.answerB);
        responseC = findViewById(R.id.answerC);
        responseD = findViewById(R.id.answerD);

        questionText = findViewById(R.id.txtQuestion);
        currentScoretxt = findViewById(R.id.currentScore);

        choiceA = findViewById(R.id.responseA);
        choiceB = findViewById(R.id.responseB);
        choiceC = findViewById(R.id.responseC);
        choiceD = findViewById(R.id.responseD);

        questionImg = findViewById(R.id.questionImg);
        //questionImg.setImageResource(R.drawable.dragon_scimitar);


        currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);
        changeQuestion();


        firstTime = preferences.getBoolean("firstTime", false);
        if(!firstTime) {
            createPopup();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        responseA.setOnClickListener(this);
        responseB.setOnClickListener(this);
        responseC.setOnClickListener(this);
        responseD.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent resultsIntent = new Intent(this, scoreBoardActivity.class);

        if (currentQuestion == 5) {
            currentScore += correctAnswer(0);
            currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);

        } else {
            switch (view.getId()) {
                case R.id.answerA:
                    currentScore += correctAnswer(0);
                    currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);
                    break;

                case R.id.answerB:
                    currentScore += correctAnswer(1);
                    currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);
                    break;

                case R.id.answerC:
                    currentScore += correctAnswer(2);
                    currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);
                    break;

                case R.id.answerD:
                    currentScore += correctAnswer(3);
                    currentScoretxt.setText(getString(R.string.currentScoretxt) + " " + currentScore);;
                    break;
            }
        }

        currentQuestion++;
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("finalScoreInt", currentScore);
        editor.apply();

        //makes sure that the currentQuestion does not go out of bounds for questionBank
        if (currentQuestion == questionBank.length) {
            startActivity(resultsIntent);
            finish();
        } else {
            changeQuestion();
        }
        /*
         * Something interesting I encountered is that is before I had just the if statement catch the currentQuestion == questionBank.length
         * and it would go into startActivity. Although, if I didn't use if/else, the app would always crash resulting in an out of bounds for
         * the QuestionClass array. So, does startActivity not start until the end of the current run? I must have missed this because I kept
         * having a crash and my solution was to contain everything in an if/else as opposed to having just a single if-statement catch the
         * counter.
         */
    }

    //Changes the question on screen
    private void changeQuestion(){
        questionText.setText(questionBank[currentQuestion].getAnswerID());

        choiceA.setText(questionBank[currentQuestion].getQuestionAID());
        choiceB.setText(questionBank[currentQuestion].getQuestionBID());
        choiceC.setText(questionBank[currentQuestion].getQuestionCID());
        choiceD.setText(questionBank[currentQuestion].getQuestionDID());

        questionImg.setImageResource(questionBank[currentQuestion].getImgID());

        if(questionBank[currentQuestion].isqIsTF()){
            responseC.setVisibility(View.GONE);
            responseD.setVisibility(View.GONE);
            choiceC.setVisibility(View.GONE);
            choiceD.setVisibility(View.GONE);

        } else {
            responseC.setVisibility(View.VISIBLE);
            responseD.setVisibility(View.VISIBLE);
            choiceC.setVisibility(View.VISIBLE);
            choiceD.setVisibility(View.VISIBLE);
        }
    }

    private int correctAnswer(int Answer){
        //Log.d("current", "test: " + questionBank[currentQuestion].isCorrectAnswer());
        if(questionBank[currentQuestion].isCorrectAnswer() == Answer) {
            //Toast.makeText(LoginTrue.this, "Correct!", Toast.LENGTH_SHORT).show();
            return 1;
        }

        //Toast.makeText(LoginTrue.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        return 0;
        //Toasts were not needed once I added score
    }

    private void createPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);

        diagText = popupView.findViewById(R.id.diagText);
        diagButton = popupView.findViewById(R.id.diagButton);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        diagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}