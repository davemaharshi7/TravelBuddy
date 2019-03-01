package com.travelbuddy.travelguideapp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.travelbuddy.travelguideapp.Activities.BaseActivity;

public class PlaceDetailsActivity extends BaseActivity {
    private static final String TAG = "MMMM";
    ConstraintLayout dynamicContent,bottonNavBar;
    FirebaseFirestore db;
    ImageView imageView;
    TextView description,name,closeDays,timing,reason;
    String placeID;
    String cityID;
    DocumentReference docRef;
    @Override
    protected void onStart() {
        super.onStart();

        Log.e("docref",docRef.toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "Document_name: " + document.toString());

                    if (document.exists()) {
                        Log.d(TAG, "Place_name: " + document.get("placeName"));
                        Log.d(TAG, "Place_description: " + document.get("placeDescription"));
                        Log.d(TAG, "Place_image: " + document.get("placeCloseDays"));
                        Log.d(TAG, "Place_Open_days: " + document.get("placeOpenTiming"));
                        name.setText(document.getString("placeName"));
                        Picasso.get().load(document.get("placeImage").toString())
                                .centerCrop().fit().into(imageView);
                        description.setText(document.get("placeDescription").toString());
                        closeDays.setText(document.get("placeCloseDays").toString());
                        timing.setText(document.get("placeOpenTiming").toString());
                        reason.setText(document.get("reasonToVisit").toString());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_place_details);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_places, dynamicContent);

        imageView = (ImageView) findViewById(R.id.placeImage);
        description = (TextView) findViewById(R.id.placeDescription);
        name =  findViewById(R.id.placeName_1);
        closeDays =  findViewById(R.id.placeCloseDays);
        timing = (TextView) findViewById(R.id.placeTiming);
        reason = (TextView) findViewById(R.id.reasonToVisit);



        //Firestore
        db = FirebaseFirestore.getInstance();
        //cityref = db.collection("Cities");

        //get the reference of RadioGroup.
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.search_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));



        placeID = getIntent().getStringExtra("placeID");
        cityID = getIntent().getStringExtra("cityID");
        docRef = db.collection("Cities")
                .document(cityID).collection("Places").document(placeID);


    }
}
