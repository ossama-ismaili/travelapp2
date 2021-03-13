package com.example.travelapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.DetailsActivity;
import com.example.travelapp.DirectionsActivity;
import com.example.travelapp.FavoritesActivity;
import com.example.travelapp.R;
import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.DirectionsData;
import com.example.travelapp.model.FavoritesData;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    Context context;
    List<FavoritesData> favoritesDataList;
    DatabaseHandler dbHandler;

    public FavoritesAdapter(Context context, List<FavoritesData> favoritesDataList) {
        this.context = context;
        this.favoritesDataList = favoritesDataList;//put data
        dbHandler=new DatabaseHandler(this.context);
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorites_row_item, parent, false);
        return new FavoritesAdapter.FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, final int position) {
        holder.countryName.setText(favoritesDataList.get(position).getCountryName());
        holder.placeName.setText(favoritesDataList.get(position).getPlaceName());
        holder.price.setText(favoritesDataList.get(position).getPrice());
        holder.placeImage.setImageResource(favoritesDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailsActivity.class);
                context.startActivity(i);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteFavorite(favoritesDataList.get(position).getFavoriteId());
                Intent i=new Intent(context, DirectionsActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesDataList.size();
    }

    public static final class FavoritesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;
        Button deleteBtn;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);
            deleteBtn=itemView.findViewById(R.id.delete_favorite);
        }
    }
}
