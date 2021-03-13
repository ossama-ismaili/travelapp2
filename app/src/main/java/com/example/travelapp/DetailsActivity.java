package com.example.travelapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.DirectionsData;

public class DetailsActivity extends AppCompatActivity {
    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        dbHandler=new DatabaseHandler(this);

        final int dirId = getIntent().getExtras().getInt("direction_id");

        DirectionsData direction= dbHandler.getDirection(dirId);

        ImageView placeImage=(ImageView)findViewById(R.id.detail_place_image);
        placeImage.setImageResource(direction.getImageUrl());

        TextView placeName=(TextView) findViewById(R.id.detail_place_name);
        placeName.setText(direction.getPlaceName());

        TextView countryName=(TextView) findViewById(R.id.detail_country_name);
        countryName.setText(direction.getCountryName());

        TextView ratingName=(TextView) findViewById(R.id.detail_rating);
        ratingName.setText(String.valueOf(direction.getRating()));

        TextView priceText=(TextView) findViewById(R.id.detail_price);
        priceText.setText(String.valueOf(direction.getPrice()));

        ImageView addFavorite=(ImageView) findViewById(R.id.detail_add_favorite);
        addFavorite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(wmbPreference.getString("user", "").length()>0){
                    if(dbHandler.insertFavorite(Integer.parseInt(wmbPreference.getString("user", "")),dirId)){
                        Intent intent = new Intent(DetailsActivity.this ,FavoritesActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_favorite_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Intent intent = new Intent(DetailsActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button bookingBtn=(Button) findViewById(R.id.booking_btn);
        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(wmbPreference.getString("user", "").length()>0){
                    if(dbHandler.bookingCount(dirId)<1){
                        Intent intent = new Intent(DetailsActivity.this ,BookingActivity.class);
                        intent.putExtra("user_id", Integer.parseInt(wmbPreference.getString("user", "")));
                        intent.putExtra("direction_id", dirId);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_favorite_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Intent intent = new Intent(DetailsActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
