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
import com.example.travelapp.model.UsersData;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbHandler=new DatabaseHandler(this);
        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        UsersData user=dbHandler.getUser(Integer.parseInt(wmbPreference.getString("user", "")));

        TextView fname = (TextView) findViewById(R.id.profile_firstname);
        TextView lname = (TextView) findViewById(R.id.profile_lastname);
        TextView username = (TextView) findViewById(R.id.profile_username);

        username.setText(user.getUsername());
        fname.setText(user.getFirstName());
        lname.setText(user.getLastName());

        ImageView homeSelector = (ImageView) findViewById(R.id.homeSelector);
        homeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView dirSelector = (ImageView) findViewById(R.id.directionsSelector);
        dirSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this ,DirectionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView favSelector = (ImageView) findViewById(R.id.favoritesSelector);
        favSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this ,FavoritesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button changepass = (Button) findViewById(R.id.buttonChangePasse);
        changepass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                TextView pass = (TextView) findViewById(R.id.editTextTextNewPass);
                TextView passconf = (TextView) findViewById(R.id.editTextTextConfNewPass);
                if ((pass.getText().toString().equals("")) || (passconf.getText().toString().equals(""))){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
                }
                else if (!pass.getText().toString().equals(passconf.getText().toString())){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                }
                else {
                   dbHandler.updatePassword(Integer.parseInt(wmbPreference.getString("user", "")), pass.getText().toString());
                }
            }
        });

        Button disconnectBtn=(Button) findViewById(R.id.disconnect_btn);
        disconnectBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SharedPreferences.Editor editor = wmbPreference.edit();
                editor.remove("user");
                editor.commit();
                Intent intent = new Intent(ProfileActivity.this ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}