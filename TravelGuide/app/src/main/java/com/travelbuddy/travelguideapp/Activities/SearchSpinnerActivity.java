package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.travelbuddy.travelguideapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class SearchSpinnerActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    Hashtable<String,String> map;
    Button submit;
    Spinner searchSpinner;
    TextView loading;
    SharedPreferences shared;

    @Override
    protected void onStart() {
        super.onStart();
        //city.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> city = new ArrayList<String>();
                            city.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("EEEE", document.getId() + " => " + document.get("CityName"));
                                city.add((String) document.get("CityName"));
                                map.put(document.getString("CityName"),document.getId());
                            }
                            Collections.sort(city);
                            ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(SearchSpinnerActivity.this, android.R.layout.simple_spinner_item, city);
                            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            searchSpinner.setAdapter(areasAdapter);
                            submit.setEnabled(true);
                            loading.setVisibility(View.INVISIBLE);

                        } else {
                            Log.d("EEEE", "Error getting documents: ", task.getException());
                        }
                    }
                });
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_spinner);

        dynamicContent = findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_search_spinner,dynamicContent);
       // dynamicContent.addView(wizard);

        shared = getSharedPreferences("Travel_Data", Context.MODE_PRIVATE);

        //get the reference of RadioGroup.
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.search_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

        loading = findViewById(R.id.loadingTextBox);
        searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        submit = (Button) findViewById(R.id.submit_search);
        submit.setEnabled(false);
        map = new Hashtable<>();
        //spinner.setOnItemClickListener();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String cityName = searchSpinner.getSelectedItem().toString();
                if (searchSpinner.getSelectedItem().toString().trim().equals("")) {
                    printMessage("Wait for Data to load!!This may be due low internet connection");
                }
                else {
                    String cityID = map.get(searchSpinner.getSelectedItem().toString());
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("city_id",cityID);
                    editor.putString("cityName",searchSpinner.getSelectedItem().toString());
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), PlacesActivity.class);
                    i.putExtra("cityID", cityID);
//                    i.putExtra("cityName",searchSpinner.getSelectedItem().toString());
                    startActivity(i);
                }
            }
        });

    }

    private void printMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
