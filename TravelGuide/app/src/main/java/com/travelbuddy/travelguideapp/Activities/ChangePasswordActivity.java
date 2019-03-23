package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travelbuddy.travelguideapp.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText old,newPass,confirmNewPass;
    private Button change;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private String email,oldPass,newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        old = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        confirmNewPass = findViewById(R.id.confirmNewPass);
        change = findViewById(R.id.changePasswordButton);
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                change.setVisibility(View.INVISIBLE);
                if(old.getText().toString().isEmpty())
                {
                    old.setError("Please Enter old Password");
                    old.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    return;
                }
                if(newPass.getText().toString().isEmpty())
                {
                    newPass.setError("Please Enter New Password");
                    newPass.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    return;
                }
                if(confirmNewPass.getText().toString().isEmpty())
                {
                    confirmNewPass.setError("Please Enter confirm Password");
                    confirmNewPass.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    return;
                }
                if(!confirmNewPass.getText().toString().equals(newPass.getText().toString()))
                {
                    confirmNewPass.setError("New Password and Confirm Password must be SAME");
                    confirmNewPass.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    return;

                }
                oldPass = old.getText().toString();
                newPassword = newPass.getText().toString();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, oldPass);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("CHANGE", "Password updated");
                                                printMessage("Password Successfully Updated");
                                                changeActivity();
                                            } else {
                                                Log.d("CHANGE", "Error password not updated");
                                                progressBar.setVisibility(View.INVISIBLE);
                                                change.setVisibility(View.VISIBLE);
                                                printMessage("Error password not updated");

                                            }
                                        }
                                    });
                                } else {
                                    Log.d("CHANGE", "Error auth failed");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    change.setVisibility(View.VISIBLE);
                                    printMessage("Error auth failed! Your Old Password might be Wrong");
                                }
                            }
                        });
            }
        });


    }

    private void changeActivity() {
        Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(i);
        finish();
        return;
    }

    private void printMessage(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
