package com.travelbuddy.travelguideapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.travelbuddy.travelguideapp.Models.HistoryDetails;
import com.travelbuddy.travelguideapp.R;

public class HistoryAdapter extends FirestoreRecyclerAdapter<HistoryDetails,HistoryAdapter.historyholder> {
    private OnItemClickListener listener;
    public HistoryAdapter(FirestoreRecyclerOptions<HistoryDetails> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final historyholder holder, int position, @NonNull HistoryDetails model) {

        holder.Date.setText(model.getDt().toString());
        DocumentReference docRef=model.getGuideDocRef();
        //DocumentReference docRef = db.collection("cities").document("SF");
        Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("HHHH", "DocumentSnapshot data: " + document.get("Guide_name"));
                        holder.guide_name.setText(document.get("Guide_name").toString());
                    } else {
                        Log.d("HHHH", "No such document");
                    }
                } else {
                    Log.d("HHHH", "get failed with ", task.getException());
                }
            }
        });

        docRef=model.getPlanDocRef();
        Task<DocumentSnapshot> documentSnapshotTask1 = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("HHHH", "DocumentSnapshot data: " + document.get("Plan_name"));
                        holder.plan_name.setText(document.get("Plan_name").toString());
                    } else {
                        Log.d("HHHH", "No such document");
                    }
                } else {
                    Log.d("HHHH", "get failed with ", task.getException());
                }
            }
        });
        holder.user_name.setText(model.getU_name());
    }

    @NonNull
    @Override
    public historyholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_historydetails,viewGroup,false);
        return new HistoryAdapter.historyholder(v);
    }

    class historyholder extends RecyclerView.ViewHolder{
        public TextView user_name;
        public TextView guide_name;
        public TextView plan_name;
        public TextView Date;
        public historyholder(View view)
        {
            super(view);
            user_name=view.findViewById(R.id.user_name);
            guide_name=view.findViewById(R.id.guide_name);
            plan_name=view.findViewById(R.id.plan_name);
            Date=view.findViewById(R.id.date);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
