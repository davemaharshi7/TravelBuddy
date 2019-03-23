package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travelbuddy.travelguideapp.R;

public class HomeActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_home, null);
        dynamicContent.addView(wizard);


        //get the reference of RadioGroup.

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.home_nav);

        // Change the corresponding icon and text color on nav button click.

//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable, 0,0);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser checkUser = FirebaseAuth.getInstance().getCurrentUser();
        if(checkUser == null){
            //user is already connected so we need to redirect to home page
//            changeActivity();
            Intent SelectionPage = new Intent(getApplicationContext(),SelectionLoginMethod.class);
            startActivity(SelectionPage);
            finish();
            return;
        }
    }
}
