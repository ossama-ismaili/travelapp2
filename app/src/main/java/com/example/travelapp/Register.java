package com.example.travelapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.database.DatabaseHandler;
import com.example.travelapp.model.UsersData;

public class Register extends AppCompatActivity {

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHandler=new DatabaseHandler(this);

        Button submit = (Button) findViewById(R.id.submit_register);
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                EditText firstNameText = (EditText) findViewById(R.id.register_firstname);
                EditText lastNameText = (EditText) findViewById(R.id.register_lastname);
                EditText usernameText = (EditText) findViewById(R.id.register_username);
                EditText passwordText = (EditText) findViewById(R.id.register_password);
                EditText passwordConfirmationText = (EditText) findViewById(R.id.register_confirm_password);
                if ((firstNameText.getText().toString().equals("")) || (lastNameText.getText().toString().equals(""))
                    || (passwordText.getText().toString().equals("")) || (passwordConfirmationText.getText().toString().equals("")))
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
                }
                else if (!passwordText.getText().toString().equals(passwordConfirmationText.getText().toString())){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                }
                else {
                    UsersData newUser=new UsersData(0, usernameText.getText().toString(), firstNameText.getText().toString(), lastNameText.getText().toString(), passwordText.getText().toString());
                    dbHandler.insertUser(newUser);
                    finish();
                }
            }
        });

    }
}