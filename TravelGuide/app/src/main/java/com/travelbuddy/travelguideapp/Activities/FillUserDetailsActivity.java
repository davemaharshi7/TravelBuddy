package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.travelbuddy.travelguideapp.R;

public class FillUserDetailsActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fill_user_details);

        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_fill_user_details, dynamicContent);

        shared = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
        String plan_id=getIntent().getStringExtra("plan_id");
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("plan_id",plan_id);
        editor.commit();


    }
}
