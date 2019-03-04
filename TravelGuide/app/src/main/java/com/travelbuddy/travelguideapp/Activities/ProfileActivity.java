package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travelbuddy.travelguideapp.R;

public class ProfileActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    private TextView name,email;
    private Button logout,verifyEmail,deleteAcc,changePasswoord;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private TextView bool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
        dynamicContent = (ConstraintLayout) findViewById(R.id.dynamicContent);
        bottonNavBar = (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_profile, dynamicContent);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton rb = (RadioButton) findViewById(R.id.account_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

        bool = (TextView) findViewById(R.id.email_verified2);
        verifyEmail = (Button) findViewById(R.id.verify);
        deleteAcc = (Button) findViewById(R.id.deleteAcc);
        name = (TextView) findViewById(R.id.nameField);
        email = (TextView) findViewById(R.id.emailField);
        changePasswoord = (Button) findViewById(R.id.changePassword);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
//                user.getUid()
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } else {

//                    name.setText(user.);
                    email.setText(user.getEmail());

                    if (user.isEmailVerified()) {
                        // user is verified, so you can finish this activity or send user to activity which you want.
                        bool.setText("Email Verified Successfully");
                        verifyEmail.setEnabled(false);
                    } else {
                        // email is not verified, so just prompt the message to the user and restart this activity.
                        printMessage("Email Not Verified! Please Verify first");
                        bool.setText("Email Not Verified ");


                    }
                }
            }
        };

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // email sent
                                    printMessage("Email Sent.Please verify and login Again");

                                    // after email is sent just logout the user and finish this activity
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                } else {
                                    // email not sent, so display message and restart the activity or do whatever you wish to do
                                    printMessage("Something Went Wrong try After Some Time!!!");

                                    //restart this activity
                                    overridePendingTransition(0, 0);
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());

                                }
                            }
                        });
            }
        });
        changePasswoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(ProfileActivity.this, ForgetPassword.class);
                startActivity(p);
                finish();
                return;
            }
        });
        //to Logout
        logout = (Button) findViewById(R.id.logout2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        printMessage("Your profile is deleted:( Create a account now!");
                                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                                        finish();
                                    } else {
                                        printMessage("Failed to delete your account!");
                                    }
                                }
                            });
                }
            }
        });


    }

    private void printMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
