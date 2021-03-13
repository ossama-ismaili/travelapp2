package com.example.travelapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.DetailsActivity;
import com.example.travelapp.R;
import com.example.travelapp.model.RecentPlacesData;

import java.util.List;

public class RecentPlacesAdapter extends RecyclerView.Adapter<RecentPlacesAdapter.RecentPlacesViewHolder> {

    Context context;
    List<RecentPlacesData> recentPlacesDataList;

    public RecentPlacesAdapter(Context context, List<RecentPlacesData> recentsDataList) {
        this.context = context;
        this.recentPlacesDataList = recentsDataList;//put data
    }

    @NonNull
    @Override
    public RecentPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);

        return new RecentPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPlacesViewHolder holder, final int position) {
        holder.countryName.setText(recentPlacesDataList.get(position).getCountryName());
        holder.placeName.setText(recentPlacesDataList.get(position).getPlaceName());
        holder.price.setText(recentPlacesDataList.get(position).getPrice());
        holder.placeImage.setImageResource(recentPlacesDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailsActivity.class);
                i.putExtra("direction_id",recentPlacesDataList.get(position).getDirectionId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentPlacesDataList.size();
    }

    public static final class RecentPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}
