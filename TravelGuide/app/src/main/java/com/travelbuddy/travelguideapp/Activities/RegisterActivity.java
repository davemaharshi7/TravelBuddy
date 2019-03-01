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
import com.travelbuddy.travelguideapp.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView log;
    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loadingProgrss;
    private Button regBtn;
    Intent home;

    private FirebaseAuth mAuth;
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
//                            updateUserInfo(name,pickedImgUri,mAuth.getCurrentUser());
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
}
