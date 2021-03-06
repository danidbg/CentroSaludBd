package com.example.centrosaludbd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events>events;
    LayoutInflater inflater;



    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate , List<Events>events) {
        super(context, R.layout.single_cell_layout);
        this.dates=dates;
        this.currentDate=currentDate;
        this.events=events;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar= Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo= dateCalendar.get(Calendar. DAY_OF_MONTH);
        int displayMonth= dateCalendar.get (Calendar.MONTH)+1;
        int displayYear= dateCalendar.get (Calendar.YEAR);
        int currentMonth=currentDate.get(Calendar.MONTH) +1;
        int currentYear=currentDate.get(Calendar.YEAR);
      View view=convertView;

       if(view ==null){
               view= inflater.inflate(R.layout.single_cell_layout,parent,false);

       }

       if(displayMonth== currentMonth && displayYear == currentYear){
        view.setBackgroundColor(getContext().getResources().getColor(R.color.green));

       }else{
           view.setBackgroundColor(Color.parseColor("#cccccc"));

       }


        TextView Day_Number = view.findViewById(R.id.calendar_day);
       Day_Number.setText(String.valueOf(DayNo));



       return view;
    }


}
