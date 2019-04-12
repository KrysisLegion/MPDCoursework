package com.example.mpd_coursework;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mpd_coursework.listeners.filterListener;

import java.util.ArrayList;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedParser feedParser = new FeedParser(this);
        feedParser.execute();



    }

    public void onReady(final FeedParser feedParser){
        final Activity self = this;
        final Spinner spinner = self.findViewById(R.id.filter);

        String[] filters = new String[]{"Display All","Date","Time","Magnitude","Location","Depth"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(self,android.R.layout.simple_spinner_item,filters);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new filterListener(self,feedParser));


    }


}

