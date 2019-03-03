package com.travelbuddy.travelguideapp.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.PlanAdapter;
import com.travelbuddy.travelguideapp.Models.Plan;
import com.travelbuddy.travelguideapp.R;

public class PlanActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview2;
    String guide_id;
    private PlanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_plan);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_plan, dynamicContent);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

        mRecyclerview2=findViewById(R.id.mRecyclerView2);
        mRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        guide_id=getIntent().getStringExtra("guide_id");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(guide_id==null)
        {
            Toast.makeText(PlanActivity.this,"Guide Not Selected",Toast.LENGTH_SHORT).show();
        }
        Query query=FirebaseFirestore.getInstance()
                .collection("Guides")
                .document(guide_id)
                .collection("Plans")
                .limit(50);

        FirestoreRecyclerOptions<Plan> options = new FirestoreRecyclerOptions.Builder<Plan>()
                .setQuery(query,Plan.class)
                .build();

        adapter=new PlanAdapter(options);
        mRecyclerview2.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
