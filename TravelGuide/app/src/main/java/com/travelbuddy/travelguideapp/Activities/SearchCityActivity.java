package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.travelbuddy.travelguideapp.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SearchCityActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    ListView lv;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    String data[];// = {"Arjun","Ankit","Arvind","Devang","Dipesh"};
    List<String> ls = new ArrayList<String>();
    Hashtable<String,String> map;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser checkUser = FirebaseAuth.getInstance().getCurrentUser();
        if(checkUser == null){
            //user is already connected so we need to redirect to home page
//            changeActivity();
            Intent SelectionPage = new Intent(getApplicationContext(),SelectionLoginMethod.class);
            startActivity(SelectionPage);
            finish();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("EEEE", document.getId() + " => " + document.get("CityName"));
                                ls.add((String) document.get("CityName"));
                                map.put(document.getString("CityName"),document.getId());
                            }
                        } else {
                            Log.d("EEEE", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_city);

        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_search_city, null);
        dynamicContent.addView(wizard);


        //get the reference of RadioGroup.
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.search_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));

        map = new Hashtable<>();
        lv = (ListView) findViewById(R.id.ListView_search);
        searchView =(SearchView) findViewById(R.id.searchID);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ls);
        lv.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ls.get(position);
                String city_id = map.get(item);
                Log.d("EEEE",item);
                Log.d("EEEE",city_id);

                Intent i = new Intent(getApplicationContext(),RecyclerPlaceView.class);
                i.putExtra("cityID",city_id);
                startActivity(i);
                finish();
                return;
            }
        });

    }


}
