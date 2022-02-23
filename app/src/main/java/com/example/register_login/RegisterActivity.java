package com.example.register_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText[] forms;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regBtn = (Button)findViewById(R.id.regBtn);

        forms = new EditText[5];
        forms[0] = (EditText)findViewById(R.id.fNameForm);
        forms[1] = (EditText)findViewById(R.id.lNameForm);
        forms[2] = (EditText)findViewById(R.id.dobForm);
        forms[3] = (EditText)findViewById(R.id.regEmailForm);
        forms[4] = (EditText)findViewById(R.id.regPassForm);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0; x < 5; x++){
                    //TextUtils.isEmpty(forms[x].toString())
                    if(forms[x].getText().toString().trim().length()==0){
                        forms[x].setError("Invalid Entry");
                        forms[x].requestFocus();
                        break;
                    }
                }
            }
        });
    }
}