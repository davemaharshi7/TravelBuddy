package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travelbuddy.travelguideapp.R;

public class SelectionLoginMethod extends AppCompatActivity {

    Button login,signup;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_login_method);
        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.login_btn_selection);
        signup = (Button) findViewById(R.id.signup_button_selection);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(s);

            }
        });
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s = new Intent(getApplicationContext(),RegisterActivity.class);
                        startActivity(s);

                    }
                }
        );


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //user is already connected so we need to redirect to home page
            Intent i = new Intent(getApplicationContext(),BaseActivity.class);
            startActivity(i);
            finish();
            return;

        }
    }
}
