package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.database.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler=new DatabaseHandler(this);
        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);

        ImageView homeSelector = (ImageView) findViewById(R.id.homeSelector);
        homeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView dirSelector = (ImageView) findViewById(R.id.directionsSelector);
        dirSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this ,DirectionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button reg = (Button) findViewById(R.id.regbutton);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.editTextTextPersonName);
                EditText pass = (EditText) findViewById(R.id.editTextTextPassword);
                if (dbHandler.checkUser(username.getText().toString(),pass.getText().toString()))
                {
                    SharedPreferences.Editor editor = wmbPreference.edit();
                    editor.putString("user", String.valueOf( dbHandler.getUserId(username.getText().toString())));
                    editor.commit();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this ,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
