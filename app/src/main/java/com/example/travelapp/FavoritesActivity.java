package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.travelapp.adapter.FavoritesAdapter;
import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.FavoritesData;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    RecyclerView favoritesRecycler;
    FavoritesAdapter favoritesAdapter;
    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        dbHandler=new DatabaseHandler(this);

        List<FavoritesData> favoritesDataList = dbHandler.getAllFavorites(Integer.parseInt(wmbPreference.getString("user", "")));
        setFavoritesRecycler(favoritesDataList);

        ImageView homeSelector = (ImageView) findViewById(R.id.homeSelector);
        homeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView userSelector = (ImageView) findViewById(R.id.userSelector);
        userSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wmbPreference.getString("user", "").length()>0){
                    Intent intent = new Intent(FavoritesActivity.this , ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(FavoritesActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ImageView dirSelector = (ImageView) findViewById(R.id.directionsSelector);
        dirSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this ,DirectionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setFavoritesRecycler(List<FavoritesData> favoritesDataList){
        favoritesRecycler = findViewById(R.id.favorites_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        favoritesRecycler.setLayoutManager(layoutManager);
        favoritesAdapter = new FavoritesAdapter(this, favoritesDataList);
        favoritesRecycler.setAdapter(favoritesAdapter);
    }
}