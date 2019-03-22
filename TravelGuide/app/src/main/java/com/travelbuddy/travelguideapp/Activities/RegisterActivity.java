package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.travelbuddy.travelguideapp.Models.UserRegister;
import com.travelbuddy.travelguideapp.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RRRR";
    private TextView log;
    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loadingProgrss;
    private Button regBtn;
    Intent home;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userEmail =(EditText) findViewById(R.id.email_input_reg);
        userName = (EditText) findViewById(R.id.name_input_reg);
        userPassword = (EditText) findViewById(R.id.pass_input_reg);
        userPassword2 = (EditText) findViewById(R.id.cpass_input_reg);
        loadingProgrss = (ProgressBar) findViewById(R.id.progressBar2);
        regBtn = (Button) findViewById(R.id.signup_btn);
        loadingProgrss.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        log = (TextView) findViewById(R.id.logLink);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
                return;
            }
        });
        home = new Intent(this,HomeActivity.class);
        shared = getSharedPreferences("Travel_Data", Context.MODE_PRIVATE);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgrss.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                final String password2 = userPassword2.getText().toString().trim();
                final String name = userName.getText().toString().trim();

                if(email.isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals
                        (password2) ){

                    //Toast for error Message
                    showMessage("Please Enter All Fields and choose Profile Photo.");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgrss.setVisibility(View.INVISIBLE);

                }
                else{

                    //All Set GO FOR Authentication FiREBASE
                    CreateUserAccount(email,name,password);
//                    String uid = mAuth.getCurrentUser().getUid();
//                    db.collection("Users").document(uid)
//                            .set()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "DocumentSnapshot successfully written!");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w(TAG, "Error writing document", e);
//                                }
//                            });
                }
            }
        });




    }

    private void CreateUserAccount(final String email,final String name, final String password) {

        //this method create user account
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Account Created
                            showMessage("Account Created");
                            //After Account Creation we need to update his profile picture
                            //updateUserInfo(name,pickedImgUri,mAuth.getCurrentUser());

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            //Toast.makeText(getApplicationContext(),"" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                            String uid = currentFirebaseUser.getUid();
//                            String uid = mAuth.getCurrentUser().getUid();
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("user_id",uid);
                            editor.putString("user_name",name);
                            editor.commit();
                            final String sha_password = getSHA(password);
                            UserRegister u = new UserRegister(name,email,sha_password);
                            db.collection("Users").document(uid)
                                    .set(u)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                            showMessage("User Registerd");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                            showMessage("ERROR in writing USer Document");
                                        }
                                    });



                            changeActivity();
                        }
                        else{
                            showMessage("Account Creation Failed :"+task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgrss.setVisibility(View.INVISIBLE);


                        }
                    }
                });


    }

    private void changeActivity() {
        startActivity(home);
        finish();
        return;
    }

    private void showMessage(String message) {

        //TODO: Make generic toast message
        Toast.makeText(getApplicationContext(),message,Toast
                .LENGTH_LONG).show();
    }

    public String getSHA(String input) {

        try {

            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);

            return null;
        }
    }

}
