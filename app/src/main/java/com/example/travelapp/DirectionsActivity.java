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

import com.example.travelapp.adapter.DirectionsAdapter;
import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.DirectionsData;

import java.util.List;

public class DirectionsActivity extends AppCompatActivity {
    RecyclerView directionsRecycler;
    DirectionsAdapter directionsAdapter;
    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        dbHandler=new DatabaseHandler(this);

        List<DirectionsData> directionsDataList = dbHandler.getAllDirections();
        setDirectionsRecycler(directionsDataList);

        ImageView homeSelector = (ImageView) findViewById(R.id.homeSelector);
        homeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectionsActivity.this ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView userSelector = (ImageView) findViewById(R.id.userSelector);
        userSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wmbPreference.getString("user", "").length()>0){
                    Intent intent = new Intent(DirectionsActivity.this , ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(DirectionsActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ImageView favSelector = (ImageView) findViewById(R.id.favoritesSelector);
        favSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wmbPreference.getString("user", "").length()>0){
                    Intent intent = new Intent(DirectionsActivity.this ,FavoritesActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(DirectionsActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setDirectionsRecycler(List<DirectionsData> directionsDataList){
        directionsRecycler = findViewById(R.id.directions_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        directionsRecycler.setLayoutManager(layoutManager);
        directionsAdapter = new DirectionsAdapter(this, directionsDataList);
        directionsRecycler.setAdapter(directionsAdapter);
    }
}