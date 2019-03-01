package com.travelbuddy.travelguideapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.travelbuddy.travelguideapp.Models.Place;
import com.travelbuddy.travelguideapp.R;

public class CityAdapter extends FirestoreRecyclerAdapter<Place,CityAdapter.CityHolder> {

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public CityAdapter(FirestoreRecyclerOptions<Place> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CityHolder holder, int position, @NonNull Place model) {
        holder.placeName.setText(model.getPlaceName());

        Picasso.get().load(model.getPlaceImage())
                .centerCrop().fit().into(holder.imageView);
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout,viewGroup,false);
        return new CityHolder(v);
    }

     public class CityHolder extends RecyclerView.ViewHolder {

        public TextView placeName;
        public ImageView imageView;


        public CityHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.card_title);
            imageView = itemView.findViewById(R.id.Card_back);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null)
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);


                }
            });

        }



    }
}
