package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Button logout,verifyEmail,deleteAcc,changePasswoord,aboutus;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private TextView bool;
    SharedPreferences shared;
    boolean doubleBackToExitPressedOnce = false;
    FirebaseUser user;

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
        aboutus = findViewById(R.id.aboutUsButton);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AboutUsActivity.class);
                startActivity(i);
            }
        });
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        shared = getSharedPreferences("Travel_Data", Context.MODE_PRIVATE);
        String user_name = shared.getString("user_name", null);
        name.setText(user_name);

        //get current user
         user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                user.getUid()
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } else {

//                    name.setText(user.);
                    email.setText(user1.getEmail());

                    if (user1.isEmailVerified()) {
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
                Intent p = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(p);

            }
        });
        //to Logout
        logout = (Button) findViewById(R.id.logout2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = shared.edit();
                editor.clear();
                editor.commit();
                auth.signOut();
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Do you want to delete your Account Permanently","This action cannot be reverted.Once you delete your Account you will loose all you personal details", "cancelMethod","deleteUser");

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
        FirebaseUser checkUser = FirebaseAuth.getInstance().getCurrentUser();
        if(checkUser == null){
            //user is already connected so we need to redirect to home page
            //changeActivity();
            Intent SelectionPage = new Intent(getApplicationContext(),SelectionLoginMethod.class);
            startActivity(SelectionPage);
            finish();
            return;
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.danger);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("DIALOG", "onClick: Cancel Called.");
                        if(cancelMethod.equals("cancelMethod")){
                            cancelMethod1();
                        }

                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("DIALOG", "onClick: OK Called.");
                        if(okMethod.equals("deleteUser")){
                            deleteAccount();
                        }
                        else if(okMethod.equals("okMethod2")){
                            //okMethod2();
                        }
                    }
                });


        builderSingle.show();
    }

    private void deleteAccount() {
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

    private void cancelMethod1() {
    }
}
