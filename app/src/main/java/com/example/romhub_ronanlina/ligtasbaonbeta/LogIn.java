package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

    //firebase object instances
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        mSignin = (Button) findViewById(R.id.signinButton);
        mSignup = (Button) findViewById(R.id.signUpButton);
        mEmailText = (EditText) findViewById(R.id.emailACText);
        mPasswordText = (EditText) findViewById(R.id.passwordEditText);

        mPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        final String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this,"Login in progress...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    showErrorDialog("There was a problem signing in.");
                }
                else{
                    Intent intent = new Intent(LogIn.this,Home.class);
                    intent.putExtra("email",email.toLowerCase());
                    finish();
                    startActivity(intent);
                }

            }
        });

    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Problem signing in!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
