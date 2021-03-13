package com.example.travelapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.database.DatabaseHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        dbHandler=new DatabaseHandler(this);

        final int dirId = getIntent().getExtras().getInt("direction_id");
        final int userId = getIntent().getExtras().getInt("user_id");

        final EditText startDateText=findViewById(R.id.booking_start_date);
        final EditText endDateText=findViewById(R.id.booking_end_date);

        Button submitBtn=(Button) findViewById(R.id.submit_booking);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if ((startDateText.getText().toString().equals("")) || (endDateText.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        Date startDate=new SimpleDateFormat("yyyy-MM-dd").parse(startDateText.getText().toString());
                        Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(endDateText.getText().toString());

                        if(dbHandler.booking(userId, dirId, startDate, endDate)){
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.booking_failed), Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}