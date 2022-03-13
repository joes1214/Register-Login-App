package com.example.register_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText emailForm, passForm;
    Button loginBtn, registerBtn;

    private String validEmail;
    private String validPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        validEmail = preferences.getString("validEmail", "admin");
        validPassword = preferences.getString("validPassword", "1234");

        //Creates the necessary values for login
        emailForm = (EditText)findViewById(R.id.emailForm);
        passForm = (EditText)findViewById(R.id.passForm);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        Intent loginIntent = new Intent(this, LoginTrue.class);
        Intent regIntent = new Intent(this, RegisterActivity.class);

        loginBtn.setOnClickListener(view -> {
            if(emailForm.getText().toString().trim().length()==0){
                emailForm.setError("Invalid Entry");
                emailForm.requestFocus();
            } else if(passForm.getText().toString().trim().length()==0){
                passForm.setError("Invalid Entry");
                passForm.requestFocus();
            }else{

                if(emailForm.getText().toString().trim().equalsIgnoreCase(validEmail) && passForm.getText().toString().trim().equalsIgnoreCase(validPassword)){
                    //Log.d("current", "Entering LoginClass");
                    startActivity(loginIntent);
                }else{
                    emailForm.setError("Invalid Entry");
                    passForm.setError("Invalid Entry");
                }
            }

        });

        registerBtn.setOnClickListener(view -> startActivity(regIntent));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}