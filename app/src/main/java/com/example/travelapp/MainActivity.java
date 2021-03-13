package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelapp.adapter.RecentPlacesAdapter;
import com.example.travelapp.adapter.TopPlacesAdapter;
import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.RecentPlacesData;
import com.example.travelapp.model.TopPlacesData;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recentRecycler, topPlacesRecycler;
    RecentPlacesAdapter recentPlacesAdapter;
    TopPlacesAdapter topPlacesAdapter;
    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler=new DatabaseHandler(this);
        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);

        List<RecentPlacesData> recentPlacesDataList = dbHandler.getAllRecentPlaces();
        setRecentRecycler(recentPlacesDataList);

        List<TopPlacesData> topPlacesDataList = dbHandler.getAllTopPlaces();
        setTopPlacesRecycler(topPlacesDataList);

        ImageView userSelector = (ImageView) findViewById(R.id.userSelector);
        userSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wmbPreference.getString("user", "").length()>0){
                    Intent intent = new Intent(MainActivity.this , ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        ImageView dirSelector = (ImageView) findViewById(R.id.directionsSelector);
        dirSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this ,DirectionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView favSelector = (ImageView) findViewById(R.id.favoritesSelector);
        favSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wmbPreference.getString("user", "").length()>0){
                    Intent intent = new Intent(MainActivity.this ,FavoritesActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        TextView seeAll=(TextView) findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this ,DirectionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setRecentRecycler(List<RecentPlacesData> recentPlacesDataList){
        recentRecycler = findViewById(R.id.recent_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentPlacesAdapter = new RecentPlacesAdapter(this, recentPlacesDataList);
        recentRecycler.setAdapter(recentPlacesAdapter);
    }

    private void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList){
        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);
    }
}
