package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.CityAdapter;
import com.travelbuddy.travelguideapp.Models.Place;
import com.travelbuddy.travelguideapp.R;

public class PlacesActivity extends BaseActivity {
    TextView it;
    ConstraintLayout dynamicContent,bottonNavBar;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    TextView emptyView;
    public CollectionReference cityref ;
    private CityAdapter adapter;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_places);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_places, dynamicContent);
//        dynamicContent.addView(wizard);

        //Firestore
        db = FirebaseFirestore.getInstance();
        cityref = db.collection("Cities");
        emptyView = findViewById(R.id.empty_view);
        //get the reference of RadioGroup.
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.search_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));




        final String item = getIntent().getStringExtra("cityID");
        Log.i("EEEE",item);
        recyclerView = (RecyclerView) findViewById(R.id.rv_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //final String value=city.getText().toString();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        String item = getIntent().getStringExtra("cityID");


        Query query = FirebaseFirestore.getInstance()
                .collection("Cities").document(item)
                .collection("Places");
        FirestoreRecyclerOptions<Place> options = new FirestoreRecyclerOptions.Builder<Place>()
                .setQuery(query,Place.class).build();

        adapter = new CityAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Place place=documentSnapshot.toObject(Place.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Intent i = new Intent(getApplicationContext(),SeparatePlaceDetailsActivity.class);
                    i.putExtra("placeID",id);
                    i.putExtra("cityID",item);
                startActivity(i);
                Log.i("EEEE",id);
            }
        });
        adapter.startListening();



    }


}
