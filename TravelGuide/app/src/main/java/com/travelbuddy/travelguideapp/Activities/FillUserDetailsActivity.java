package com.travelbuddy.travelguideapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.travelbuddy.travelguideapp.Models.GuideDetails;
import com.travelbuddy.travelguideapp.Models.HistoryDetails;
import com.travelbuddy.travelguideapp.Models.Plan;
import com.travelbuddy.travelguideapp.R;

import java.util.HashMap;
import java.util.Map;

public class FillUserDetailsActivity extends BaseActivity {
    ConstraintLayout dynamicContent,bottonNavBar;
    FirebaseFunctions mFunctions;
    SharedPreferences shared;
    EditText u_name;
    EditText u_contact;
    EditText u_address;
    EditText u_comments;
    EditText u_persons;
    Button u_submit;
    String plan_id;
    String guide_id,uid;
    String user_name;
    Long contact;
    String address;
    String comments;
    FirebaseUser currentFirebaseUser;
    Long persons;
    Button update_history;
    private ProgressBar pg;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public GuideDetails gd = new GuideDetails();
    public Plan pd = new Plan();
    public HistoryDetails hd = new HistoryDetails();
    DocumentReference docref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fill_user_details);

        dynamicContent = (ConstraintLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (ConstraintLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_fill_user_details, dynamicContent);

        u_name=findViewById(R.id.user_name);
        u_persons=findViewById(R.id.u_persons);
        u_address=findViewById(R.id.u_address);
        u_comments=findViewById(R.id.u_comments);
        u_submit = findViewById(R.id.u_submit);
        u_contact=findViewById(R.id.u_contact);
        pg = findViewById(R.id.progressBar3);
        update_history=(Button)findViewById(R.id.u_submit);
        pg.setVisibility(View.INVISIBLE);
        shared = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.hire_nav);
        rb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rb.setTextColor(getResources().getColor(R.color.white));
        plan_id=getIntent().getStringExtra("plan_id");
        guide_id =  shared.getString("guide_id","ERROR");
        uid = shared.getString("user_id","ERROR");
        //shared.getString("")Add user Id
        if(TextUtils.equals(guide_id,"ERROR"))
        {
            Toast.makeText(getApplicationContext(),"Please Select Guide First!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FillUserDetailsActivity.this,GuideHireActivity.class);
            startActivity(intent);
            finish();
        }
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("plan_id",plan_id);
        editor.commit();

        Log.d("MMMM",guide_id+"  "+plan_id);
        mFunctions = FirebaseFunctions.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    }


    public void UpdateHistory(View view)
    {
        //HistoryDetails hd;


        if(u_name.getText().toString().isEmpty())
        {
            u_name.setError("Name cannot be Empty");
            u_name.requestFocus();
            return;
        }
        if(u_contact.getText().toString().isEmpty() ||
            u_contact.getText().toString().length() < 10 )
        {
            u_contact.setError("Contact number either Empty or less than 10 digits!");
            u_contact.requestFocus();
            return;
        }
        if(u_address.getText().toString().isEmpty())
        {
            u_address.setError("Address Can not be Empty");
            u_address.requestFocus();
            return;
        }
        if(u_persons.getText().toString().isEmpty())
        {
            u_persons.setError("Please Enter Number of Persons Travelling to trip");
            u_persons.requestFocus();
            return;
        }
        if(u_comments.getText().toString().isEmpty())
        {
            u_comments.setError("Comment cannot be Empty");
            u_comments.requestFocus();
            return;
        }
        u_submit.setVisibility(View.INVISIBLE);
        pg.setVisibility(View.VISIBLE);
        user_name=u_name.getText().toString();
        contact= Long.parseLong(u_contact.getText().toString());
        address=u_address.getText().toString();
        comments=u_comments.getText().toString();
        persons=Long.parseLong(u_persons.getText().toString());
        //final GuideDetails[] gd = new GuideDetails[1];
        //final Plan[] pd = new Plan[1];
        DocumentReference docRef = db.collection("Guides").document(guide_id);
        hd.setGuideDocRef(docRef);
        //Log.d("MMMM","Hellooo");
        //Log.d("MMMM","  "+hd.getGuideDetails().getGuide_name());
        //Log.d("MAHA","   "+gd.getGuide_name());
        //Plan pd;
        docRef = db.collection("Guides").document(guide_id).collection("Plans").document(plan_id);
        hd.setPlanDocRef(docRef);
        hd.setU_id(uid);
        hd.setU_name(user_name);
        hd.setU_address(address);
        hd.setU_persons(persons);
        hd.setU_comments(comments);
        hd.setU_contact(contact);
        //hd.setDt(FieldValue.serverTimestamp());
        //Log.d("MMMM",pd.getPlan_name()+" "+pd.getPlan_places());
        //hd=new HistoryDetails(pd,gd,user_name,address,contact,comments,persons);
        Map<String,Object> updates = new HashMap<>();
        updates.put("timestamp", FieldValue.serverTimestamp());
//        final Task<DocumentReference> task= db.collection("History").add(hd);
//
//        task.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                docref1=task.getResult();
//            }
//        });
//
//        docref1.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            // [START_EXCLUDE]
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {}
//            // [START_EXCLUDE]
//        });
        db.collection("History").add(hd);
        String email = currentFirebaseUser.getEmail();
        Log.d("EMAIL",email);

//        Toast.makeText(FillUserDetailsActivity.this,"Your trip is calculated",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),ConfirmActivity.class);
        intent.putExtra("emailUser",email);
        intent.putExtra("username",user_name);
        intent.putExtra("persons",persons.toString());
        intent.putExtra("contact",contact.toString());

        startActivity(intent);
        finish();
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
    }
}
