package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.GuideAdapter;
import com.travelbuddy.travelguideapp.Models.GuideDetails;
import com.travelbuddy.travelguideapp.R;

public class GuideHireActivity extends BaseActivity {

    ConstraintLayout dynamicContent,bottonNavBar;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView1;
    private GuideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_guide_hire);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_guide_hire, dynamicContent);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
        mRecyclerView1=findViewById(R.id.mRecyclerView1);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=FirebaseFirestore.getInstance()
                .collection("Guides")
                .whereEqualTo("Current_city","Ahmedabad").whereEqualTo("Available",true)
                .limit(50);
        FirestoreRecyclerOptions<GuideDetails> options = new FirestoreRecyclerOptions.Builder<GuideDetails>()
                .setQuery(query,GuideDetails.class)
                .build();
        adapter = new GuideAdapter(options);
        mRecyclerView1.setAdapter(adapter);
        adapter.setOnItemClickListener(new GuideAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                GuideDetails guide=documentSnapshot.toObject(GuideDetails.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                //Toast.makeText(GuideHireActivity.this,
                  //    "Position: " + position + "  ID:  " + id + "  \nPath " + path, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GuideHireActivity.this,PlanActivity.class);
                i.putExtra("guide_id",id);
                //i.putExtra("city",value);
                //i.putExtra("Value2", "Simple Tutorial");
                // Set the request code to any code you like, you can identify the
                // callback via this code
                startActivity(i);
            }
        });
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
