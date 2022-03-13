package com.example.register_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    EditText[] forms;
    Button regBtn, dobForm;
    private DatePickerDialog datePickerDialog; //https://www.youtube.com/watch?v=qCoidM98zNk is where I got this
    private String validEmail, validPassword;

    //This whole section is a mess. I'm so sorry
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDatePicker();

        //Creates a toast, linked below is where I got it
        Context context = getApplicationContext(); //https://developer.android.com/guide/topics/ui/notifiers/toasts#java for toasts
        CharSequence text = "Successful Registration!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        //I think this returns you to the main page? Or does this start a whole new activity on top
        //of what was already created?
        Intent regReturn = new Intent(this, MainActivity.class);

        //Creating variables for buttons and forms
        regBtn = (Button)findViewById(R.id.regBtn);
        dobForm = (Button)findViewById(R.id.dobForm);

        forms = new EditText[4];
        forms[0] = (EditText)findViewById(R.id.fNameForm);
        forms[1] = (EditText)findViewById(R.id.lNameForm);
        forms[2] = (EditText)findViewById(R.id.regEmailForm);
        forms[3] = (EditText)findViewById(R.id.regPassForm);

        //sets today's date to the Date of Birth section
        dobForm.setText(getTodaysDate());

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0; x < 4; x++){
                    //I couldn't get TextUtils to work properly. I think I know why.
                    //TextUtils.isEmpty(forms[x].toString())
                    //Checks to see if the string is empty
                    if(forms[x].getText().toString().trim().length()==0){
                        forms[x].setError("Invalid Entry");
                        forms[x].requestFocus();
                    }
                }
                //Afterwards checks to see if all the information is correct
                if(validInfo()){
                    //replaces the SharedPreferences from admin;1234 to whatever the user sets
                    validEmail = forms[2].getText().toString().trim();
                    validPassword = forms[3].getText().toString().trim();

                    SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("validEmail", validEmail);
                    editor.putString("validPassword", validPassword);
                    editor.apply();

                    toast.show();
                    startActivity(regReturn);
                }
            }
        });
    }
    //The purpose of this was to make sure I was able to validate all the entered variables
    //and also give them all an error.
    private Boolean validInfo(){
        int y = 0;
        for (int x = 0; x < 4; x++){
            if(validateForms(x, forms[x].getText().toString())) {
                y++;
            }
            if (y == 4) {
                return true;
            }
        }
        return false;
    }

    //Returns the current day
    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month += 1;

        return month + "/" + day + "/" + year;

    }
    //This function allows me to create a dialog box to prompt the user to enter in a proper DOB
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date = month + "/" + day + "/" + year;
                dobForm.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    //This validates the inputs and makes sure they're correct
    private Boolean validateForms(int Type, String Text){
        switch (Type){
            case 0: //First Name
            case 1://Last name; they do the same thing
                if (Text.length() < 3){
                    forms[Type].setError("Name too short!");
                    return false;
                }

                if(Text.length() > 30){
                    forms[Type].setError("Name too long!");
                    return false;
                }
                return true;

            case 2: //email
                String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //got the regex code from here https://youtu.be/o9Y7HDkopHg?t=392
                if (!Text.matches(emailRegex)){
                    forms[Type].setError("Enter valid Email!");
                    return false;
                }
                return true;

            case 3: //password
                if(Text.length() < 8){
                    forms[Type].setError("Password must be at least 8 characters!");
                    return false;
                }
                return true;

            //The program should never arrive here
            default:
                return false;

        }

    }

    public void openDate(View view) {
        datePickerDialog.show();
    }
}