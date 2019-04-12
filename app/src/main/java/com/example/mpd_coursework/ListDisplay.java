package com.example.mpd_coursework;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.example.mpd_coursework.listeners.QuakeClickListener;

import java.util.ArrayList;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


/*
 * This displays the list of earthquakes into the ListView
 */
public class ListDisplay {
    public void displayAll(Context context, ArrayList<Quake> quakes){

        Activity activity = (Activity)context;
        ListView listView =  activity.findViewById(R.id.listView);
        listView.invalidate();

        CustomAdaptor customAdaptor = new CustomAdaptor(context,quakes.size(),quakes);
        listView.setAdapter(customAdaptor);
        customAdaptor.notifyDataSetChanged();

        listView.setOnItemClickListener(new QuakeClickListener(quakes,activity));
        
    }

}
