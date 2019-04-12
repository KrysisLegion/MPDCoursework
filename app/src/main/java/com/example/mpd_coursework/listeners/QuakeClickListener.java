package com.example.mpd_coursework.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.mpd_coursework.MapActivity;
import com.example.mpd_coursework.Quake;

import java.util.ArrayList;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


/*
 *  This displays the information for the earthquake on the Map View
 *
 */
public class QuakeClickListener implements android.widget.AdapterView.OnItemClickListener {

    private ArrayList<Quake> quakes;
    private Activity self;

    public QuakeClickListener(ArrayList<Quake> quakes, Activity self){
        this.quakes = quakes;
        this.self = self;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Quake quake = quakes.get(position);

        Intent intent = new Intent(self, MapActivity.class);
        intent.putExtra("Location",quake.getLocation());
        intent.putExtra("Date",quake.getDate());
        intent.putExtra("Time",quake.getTime());
        intent.putExtra("Depth",quake.getDepth());
        intent.putExtra("Magnitude",quake.getMagnitude());
        intent.putExtra("Latitude",quake.getLatitude());
        intent.putExtra("Longitude",quake.getLongitude());
        self.startActivity(intent);

    }
}
