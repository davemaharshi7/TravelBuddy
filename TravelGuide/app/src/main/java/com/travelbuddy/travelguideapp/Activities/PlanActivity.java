package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.GuideAdapter;
import com.travelbuddy.travelguideapp.Adapter.PlanAdapter;
import com.travelbuddy.travelguideapp.Models.GuideDetails;
import com.travelbuddy.travelguideapp.Models.Plan;
import com.travelbuddy.travelguideapp.R;

public class PlanActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview2;
    String guide_id,guide_name;
    TextView guideData;
    SharedPreferences shared;
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

        shared = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);
        mRecyclerview2=findViewById(R.id.mRecyclerView2);
        mRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        guide_id = getIntent().getStringExtra("guide_id");
        guide_name = getIntent().getStringExtra("guide_name");
        guideData =findViewById(R.id.guideData);
        guideData.setText("Plans of "+guide_name+":");
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("guide_id",guide_id);
        editor.commit();

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
        adapter.setOnItemClickListener(new PlanAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Plan plan=documentSnapshot.toObject(Plan.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(PlanActivity.this,
                    "Position: " + position + "  ID:  " + id + "  \nPath " + path, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PlanActivity.this,FillUserDetailsActivity.class);
                i.putExtra("plan_id",id);
                //i.putExtra("city",value);
                //i.putExtra("Value2", "Simple Tutorial");
                // Set the request code to any code you like, you can identify the
                // callback via this code
                startActivity(i);
            }
        });
        //adapter.startListening();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
