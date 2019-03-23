package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travelbuddy.travelguideapp.Adapter.SliderAdapter;
import com.travelbuddy.travelguideapp.R;

public class IntroTutorialActivity extends AppCompatActivity {


    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;
    private SliderAdapter sliderAdapter;

    private Button mBack;
    private Button mNext,skip;
    private int mCurrentPage;
    //View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_tutorial);

        //TODO:Code checking if user opened app First time
//        SharedPreferences pref = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
//        String FirstTime = pref.getString("firstTime","");
//        if(FirstTime.equals("No")){
//
//            changeActivity();
//
//        }else {
//
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("firstTime","No");
//            editor.apply();
//
//        }

        skip = findViewById(R.id.skipbtn);
        mSlideViewPager = (ViewPager) findViewById(R.id.SlideLayout);
        mDotLayout = (LinearLayout) findViewById(R.id.Linear);

        mBack = (Button) findViewById(R.id.back_btn);
        mNext = (Button) findViewById(R.id.next_btn);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSlideViewPager.getCurrentItem() == mDots.length - 1) {
                    Toast.makeText(IntroTutorialActivity.this, "Finished!", Toast.LENGTH_LONG).show();
                    //Goto my intent
                    changeActivity();
                } else {
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1, true);
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1, true);
            }
        });

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        //View view = this.getWindow().getDecorView();

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    private void changeActivity() {
        Intent intent = new Intent(IntroTutorialActivity.this, SelectionLoginMethod.class);
        startActivity(intent);
        finish();
        return;
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[5];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparentWhite));
            mDotLayout.addView(mDots[i]);


        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;

            //view.setBackgroundColor(sliderAdapter.slide_bg[mCurrentPage-1]);




            if (i == 0) {
                mNext.setEnabled(true);
                mBack.setEnabled(false);
                mBack.setVisibility(View.INVISIBLE);

                mNext.setText("Next");
                mBack.setText("");
            } else if (i == mDots.length - 1) {

                mNext.setEnabled(true);
                mBack.setEnabled(true);
                mBack.setVisibility(View.VISIBLE);

                mNext.setText("Finish");
                mBack.setText("Back");
            } else {

                mNext.setEnabled(true);
                mBack.setEnabled(true);
                mBack.setVisibility(View.VISIBLE);

                mNext.setText("Next");
                mBack.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
