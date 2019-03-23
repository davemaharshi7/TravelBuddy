package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.travelbuddy.travelguideapp.R;

public class SeparatePlaceDetailsActivity extends BaseActivity{
    private static final String TAG = "MMMM";
    ConstraintLayout dynamicContent, bottonNavBar;
    private FirebaseFirestore db;
    private ImageView imageView;
    private TextView name1,description,reason,closedays,timing;
    String placeID,cityID;

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
        //setContentView(R.layout.activity_separate_place_details);


        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_separate_place_details, dynamicContent);

        placeID = getIntent().getStringExtra("placeID");
        cityID = getIntent().getStringExtra("cityID");

        //Firestore
        db = FirebaseFirestore.getInstance();
        //cityref = db.collection("Cities");

        //get the reference of RadioGroup.
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.search_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

        name1 = (TextView) findViewById(R.id.NameField);
        imageView = (ImageView) findViewById(R.id.imageView5);
        description = (TextView) findViewById(R.id.DescriptionField);
        reason = (TextView) findViewById(R.id.ReasonField);
        closedays = (TextView) findViewById(R.id.CloseDaysField);
        timing = (TextView) findViewById(R.id.OpenTimingField);


        DocumentReference dref = db.collection("Cities")
                .document(cityID).collection("Places").document(placeID);
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    String nameField = document.getString("placeName");
                    String descriptionField = document.getString("placeDescription");
                    String closeDaysField = document.getString("placeCloseDays");
                    String openTiming = document.getString("placeOpenTiming");
                    String reasonToVisit = document.getString("reasonToVisit");
                    String imageField = document.get("placeImage").toString();
                    Picasso.get().load(imageField).centerCrop().fit().into(imageView);
                    name1.setText(nameField);
                    description.setText(descriptionField);
                    reason.setText(reasonToVisit);
                    closedays.setText(closeDaysField);
                    timing.setText(openTiming);
                }
            }
        });

    }
}
