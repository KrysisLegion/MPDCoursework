package com.example.mpd_coursework;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mpd_coursework.Quake;
import com.example.mpd_coursework.R;

import java.util.ArrayList;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */

/*
 * This class is used to create a custom view to display the required information inside each of the list entries
 */
public class CustomAdaptor extends BaseAdapter {
    private int size;
    private Context context;
    private ArrayList<Quake> quakes;

    public CustomAdaptor(Context context, int size, ArrayList<Quake> quakes){
        this.size = size;
        this.context = context;
        this.quakes = quakes;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.customlayout,null);
        TextView location = view.findViewById(R.id.textView_Location);
        TextView latitude = view.findViewById(R.id.textView_Latitude);
        TextView longitude = view.findViewById(R.id.textView_Longitude);
        TextView stat = view.findViewById(R.id.stat);

        location.setText(quakes.get(i).getLocation());
        latitude.setText(quakes.get(i).getLatitude());
        longitude.setText(quakes.get(i).getLongitude());
        stat.setText(quakes.get(i).getStat());

        return view;
    }
}