package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LogIn extends AppCompatActivity {

    //UI object insance
    private Button mSignin;
    private Button mSignup;
    private EditText mEmailText;
    private EditText mPasswordText;

    //member vars
    private String email;
    private String password;

    //firebase object instances
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        mSignin = (Button) findViewById(R.id.signinButton);
        mSignup = (Button) findViewById(R.id.signupButton);
        mEmailText = (AutoCompleteTextView) findViewById(R.id.emailACText);
        mPasswordText = (EditText) findViewById(R.id.passwordEditText);

        email = mEmailText.getText().toString();
        password = mPasswordText.getText().toString();

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInputs(email,password) == true){
                    attemptLogin();
                }
                else{
                    //message
                }
            }
        });
    }

    public boolean checkInputs(String email, String password){

        if(email == null && password == null){ return false; }

        return true;
    }

    private void attemptLogin(){

    }
}
