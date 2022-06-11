package com.example.centrosaludbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalendarMain extends AppCompatActivity {

    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView=(CalendarView)findViewById(R.id.custom_Calendar_View);
    }
}