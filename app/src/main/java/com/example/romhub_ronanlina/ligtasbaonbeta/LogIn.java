package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        //flag variable
        final boolean adminChecker = isAdmin(email,password);

        //Sign in click
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInputs(email, password) && !adminChecker){
                    attemptLogin(adminChecker);
                }
                else if(checkInputs(email, password) && adminChecker){
                    //open add activity
                    attemptLogin(adminChecker);
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

    public boolean isAdmin(String email, String password){

        if(email.toLowerCase() == "ligtasbaon@gmail.com" && password.toLowerCase() == "password"){
            return true;
        }

        return false;
    }

    private void attemptLogin(final boolean adminChecker){

        Toast.makeText(this,"Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    showErrorDialog("There was a problem signing in.");
                }
                else if(task.isSuccessful() && adminChecker){
                    //open admin activity
                    Intent intent = new Intent(LogIn.this,Admin.class);
                    finish();
                    startActivity(intent);
                }
                else if(task.isSuccessful() && !adminChecker){

                    //open non admin activity
                    /*Intent intent = new Intent(LogIn.this,Admin.class);
                    finish();
                    startActivity(intent);*/
                }
            }
        });

    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
