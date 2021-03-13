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
import com.example.travelapp.model.DirectionsData;
import java.util.List;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.DirectionsViewHolder> {
    Context context;
    List<DirectionsData> directionsDataList;

    public DirectionsAdapter(Context context, List<DirectionsData> directionsDataList) {
        this.context = context;
        this.directionsDataList = directionsDataList;//put data
    }

    @NonNull
    @Override
    public DirectionsAdapter.DirectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.directions_row_item, parent, false);
        return new DirectionsAdapter.DirectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionsAdapter.DirectionsViewHolder holder, final int position) {
        holder.countryName.setText(directionsDataList.get(position).getCountryName());
        holder.placeName.setText(directionsDataList.get(position).getPlaceName());
        holder.price.setText(directionsDataList.get(position).getPrice());
        holder.placeImage.setImageResource(directionsDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailsActivity.class);
                i.putExtra("direction_id",directionsDataList.get(position).getDirectionId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return directionsDataList.size();
    }

    public static final class DirectionsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public DirectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}