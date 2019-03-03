package com.travelbuddy.travelguideapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.travelbuddy.travelguideapp.Models.GuideDetails;
import com.travelbuddy.travelguideapp.R;

public class GuideAdapter  extends FirestoreRecyclerAdapter<GuideDetails,GuideAdapter.guide_holder> {
    private OnItemClickListener listener;

    public GuideAdapter(FirestoreRecyclerOptions<GuideDetails> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull guide_holder holder, int position, @NonNull GuideDetails model) {
        holder.guide_name.setText(model.getGuide_name());
        holder.city.setText(model.getCurrent_city());
        holder.gender.setText(model.getGender());
        holder.language.setText(model.getLanguage());
        holder.ratings.setText(model.getRatings().toString()+" Star");
    }
    @NonNull
    @Override
    public guide_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_guide_details,viewGroup,false);
        return new guide_holder(v);
    }

    class guide_holder extends RecyclerView.ViewHolder
        {

                public TextView guide_name;
                public TextView city;
                public TextView ratings;
                public TextView language;
                public TextView gender;
                public guide_holder(View view)
                {
                    super(view);
                    guide_name=view.findViewById(R.id.guide_name1);
                    city=view.findViewById(R.id.guide_city);
                    ratings=view.findViewById(R.id.guide_ratings);
                    language=view.findViewById(R.id.guide_language);
                    gender=view.findViewById(R.id.guide_gender);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION && listener !=null)
                                listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }
                    });
                }
        }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
