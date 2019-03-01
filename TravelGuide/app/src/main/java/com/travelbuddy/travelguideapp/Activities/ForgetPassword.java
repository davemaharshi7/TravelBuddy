package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.travelbuddy.travelguideapp.R;

public class ForgetPassword extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        inputEmail = (EditText) findViewById(R.id.email_forgetpass);
        btnReset = (Button) findViewById(R.id.submit_forgetpass);
//        btnBack = (Button) findViewById(R.id.back_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_forgetpass);
        progressBar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    changeActivity();
                                } else {
                                    Toast.makeText(ForgetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

    }

    private void changeActivity() {
        Intent login = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(login);
        finish();
        return;
    }
}
