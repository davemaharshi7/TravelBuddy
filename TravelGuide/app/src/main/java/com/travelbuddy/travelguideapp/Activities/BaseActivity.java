package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.travelbuddy.travelguideapp.R;

public class BaseActivity extends AppCompatActivity {
    RadioGroup radioGroup1;
    RadioButton deals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        deals = (RadioButton)findViewById(R.id.home_nav);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent in;
                Log.i("matching", "matching inside1 bro" + checkedId);
                switch (checkedId)
                {
                    case R.id.home_nav:
                        Log.i("matching", "matching inside1 matching" +  checkedId);
                        in=new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.search_nav:
                        Log.i("matching", "matching inside1 watchlistAdapter" + checkedId);

                        in = new Intent(getBaseContext(), SearchSpinnerActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.hire_nav:
                        Log.i("matching", "matching inside1 rate" + checkedId);

                        in = new Intent(getBaseContext(),GuideHireActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.history_nav:
                        Log.i("matching", "matching inside1 listing" + checkedId);
                        in = new Intent(getBaseContext(), History.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.account_nav:
                        Log.i("matching", "matching inside1 deals" + checkedId);
                        in = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
