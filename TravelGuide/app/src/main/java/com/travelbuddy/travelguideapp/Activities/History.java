package com.travelbuddy.travelguideapp.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Models.HistoryDetails;
import com.travelbuddy.travelguideapp.Models.Plan;
import com.travelbuddy.travelguideapp.R;

public class History extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_history, dynamicContent);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query=FirebaseFirestore.getInstance()
                .collection("History")
                .limit(50);


        FirestoreRecyclerOptions<HistoryDetails> options = new FirestoreRecyclerOptions.Builder<HistoryDetails>()
                .setQuery(query,HistoryDetails.class)
                .build();

    }
}
