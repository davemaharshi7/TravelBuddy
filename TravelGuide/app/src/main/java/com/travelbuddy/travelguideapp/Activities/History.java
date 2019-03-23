package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.HistoryAdapter;
import com.travelbuddy.travelguideapp.Models.HistoryDetails;
import com.travelbuddy.travelguideapp.Models.Plan;
import com.travelbuddy.travelguideapp.R;

public class History extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    private HistoryAdapter historyAdapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview3;
    SharedPreferences shared;
    String user_id;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_history, dynamicContent);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.history_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
        mRecyclerview3=findViewById(R.id.mRecyclerView3);
        mRecyclerview3.setLayoutManager(new LinearLayoutManager(this));
        shared = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);
        user_id=shared.getString("user_id","Error");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser checkUser = FirebaseAuth.getInstance().getCurrentUser();
        if(checkUser == null){
            Intent SelectionPage = new Intent(getApplicationContext(),SelectionLoginMethod.class);
            startActivity(SelectionPage);
            finish();
            return;
        }
        Query query=FirebaseFirestore.getInstance()
                .collection("History")
                .whereEqualTo("u_id",user_id)
                .limit(50);


        FirestoreRecyclerOptions<HistoryDetails> options = new FirestoreRecyclerOptions.Builder<HistoryDetails>()
                .setQuery(query,HistoryDetails.class)
                .build();

        historyAdapter=new HistoryAdapter(options);
        mRecyclerview3.setAdapter(historyAdapter);
        historyAdapter.startListening();

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
    protected void onStop() {
        super.onStop();
        historyAdapter.stopListening();
    }
}
