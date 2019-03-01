package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travelbuddy.travelguideapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private Intent home;
    private TextView reg,forgetPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email_Input);
        password = (EditText) findViewById(R.id.password_Input);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        login = (Button) findViewById(R.id.loginBtn);
        forgetPass = (TextView) findViewById(R.id.forgetPass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(i);

            }
        });
        // Initialize Firebase Auth Variable
        mAuth = FirebaseAuth.getInstance();

        home = new Intent(this,HomeActivity.class);
//        if (mAuth.getCurrentUser() != null) {
//            // User is signed in (getCurrentUser() will be null if not signed in)
//            changeActivity();
//        }
        reg = (TextView) findViewById(R.id.regLink);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
                finish();
                return;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);

                final String log_email = email.getText().toString().trim();
                final String log_pass = password.getText().toString().trim();

                if(log_email.isEmpty() || log_pass.isEmpty()){
                    showMessage("Please Enter All Fields");
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }else {
                    signIn(log_email,log_pass);
                }
            }
        });
    }

    private void signIn(String log_email, String log_pass) {

        mAuth.signInWithEmailAndPassword(log_email,log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                    changeActivity();
                }else {
                    showMessage("Login Error Occured : " + task.getException().getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void changeActivity() {
        startActivity(home);
        finish();
        return;
    }

    private void showMessage(String msg) {
        //TODO: TOAST Message
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //TODO: PlEASE Redirect user to home page
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //user is already connected so we need to redirect to home page
            changeActivity();

        }

    }
}
