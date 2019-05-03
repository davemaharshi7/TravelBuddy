package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.travelbuddy.travelguideapp.R;

import java.util.HashMap;
import java.util.Map;

public class ConfirmActivity extends BaseActivity {
    private FirebaseFunctions mFunctions;
    ConstraintLayout dynamicContent,bottonNavBar;
    SharedPreferences sharedPreferences;
    private Button home;
    String emailGuide,guideName,guideId;
    String emailUser,user_name,no_persons,contact;
    FirebaseFirestore db;
//    private boolean doubleBackToExitPressedOnce = false;

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
//        setContentView(R.layout.activity_confirm);
        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_confirm, dynamicContent);

        sharedPreferences = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
        home = findViewById(R.id.homeBtn);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        emailUser = intent.getStringExtra("emailUser");
        user_name= intent.getStringExtra("username");
        no_persons = intent.getStringExtra("persons");
        contact = intent.getStringExtra("contact");
        guideId = sharedPreferences.getString("guide_id","");

        db.collection("Guides").document(guideId).update("Available",false);
        db.collection("Guides").document(guideId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Log.d("TEST", "DocumentSnapshot data: " + document.getData());
                        emailGuide = document.get("Guide_email").toString();
                        guideName = document.get("Guide_name").toString();
                        //EMAIL TO GUIDE--
                        addMessageToGuide(emailGuide, guideName, user_name, no_persons, contact)
                                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("DD","FAILED to Send EMAIL TO GUIDE!");
                            }
                        }).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(getApplicationContext(),"Notification Send To Guide",Toast
                                        .LENGTH_SHORT)
                                        .show();
                            }
                        });


                    } else {
                        Log.d("TEST", "No such document");
                    }
                } else {
                    Log.d("TEST", "get failed with ", task.getException());
                }
            }
        });


        mFunctions = FirebaseFunctions.getInstance();
//        mFunctions.getHttpsCallable("")
        addMessage(emailUser, user_name, no_persons, contact).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DD","FAILED");
            }
        }).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Toast.makeText(getApplicationContext(),"MAIL SEND",Toast.LENGTH_SHORT).show();
            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    private Task addMessage(String emailUser,String user_name,String no_persons,String contact) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("emailUser", emailUser);
        data.put("username", user_name);
        data.put("persons", no_persons);
        data.put("contact", contact);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("sendEmailToUser")
                .call(data);
    }
    private Task addMessageToGuide(String emailUser,String guidename,String user_name,String
            no_persons,String
            contact) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("emailUser", emailUser);
        data.put("guidename", guidename);
        data.put("username", user_name);
        data.put("persons", no_persons);
        data.put("contact", contact);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("sendEmailToGuide")
                .call(data);
    }

}
