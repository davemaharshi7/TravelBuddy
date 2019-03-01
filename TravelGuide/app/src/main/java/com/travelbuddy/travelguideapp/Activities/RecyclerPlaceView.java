package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.travelbuddy.travelguideapp.Adapter.CityAdapter;
import com.travelbuddy.travelguideapp.Models.Place;
import com.travelbuddy.travelguideapp.R;

public class RecyclerPlaceView extends AppCompatActivity {

    RecyclerView recyclerView;
    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_place_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String item = getIntent().getStringExtra("cityID");


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
//                Intent i = new Intent(getApplicationContext(),Place.class);
//                i.putExtra("place",id);
//                startActivity(i);
                Log.i("EEEE",id);
            }
        });
        adapter.startListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
