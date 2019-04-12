package com.example.mpd_coursework.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mpd_coursework.FeedParser;
import com.example.mpd_coursework.ListDisplay;
import com.example.mpd_coursework.Quake;
import com.example.mpd_coursework.Search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


public class searchInput implements View.OnClickListener {
    private Activity self;
    private Search search;
    private FeedParser feedParser;

    public searchInput(FeedParser feedParser, Activity self, Search search) {
        this.self = self;
        this.search = search;
        this.feedParser = feedParser;
    }

    @Override
    public void onClick(View v) {
        if(search.getType().equals("Date")){
            dateFilter();
        }else if(search.getType().equals("Time")){
            timeFilter();
        }else if(search.getType().equals("Magnitude")){
            magnitudeFilter();
        }else if(search.getType().equals("Depth")){
            depthFilter();
        }else if(search.getType().equals("Location")){
            locationFilter();
        }
    }

    /*
     * This filters the earthquakes between two dates
     */
    private void dateFilter(){
        EditText startDate = (EditText) search.getInputs()[0];
        EditText endDate = (EditText) search.getInputs()[1];

        ArrayList<Quake> quakes = new ArrayList<Quake>();
        for(Quake quake : feedParser.getQuakes()) {
            try {
                Date start = new SimpleDateFormat("dd MMM yyyy").parse(startDate.getText().toString());
                Date end = new SimpleDateFormat("dd MMM yyyy").parse(endDate.getText().toString());
                Date search = new SimpleDateFormat("dd MMM yyyy").parse(quake.getDate().substring(4, quake.getDate().length()));

                if(search.after(start) && search.before(end)){
                    quakes.add(quake);
                    quakes = addStats(quakes);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        displayToast(quakes);
    }

    /*
     * This filters the earthquakes by magnitude
     */
    private void magnitudeFilter(){

        EditText lowestMagEdit = (EditText) search.getInputs()[0];
        EditText highestMagEdit = (EditText) search.getInputs()[1];
        ArrayList<Quake> quakes = new ArrayList<>();
        for(Quake quake : feedParser.getQuakes()){
            if(Double.parseDouble(quake.getMagnitude()) > Double.parseDouble(lowestMagEdit.getText().toString()) && Double.parseDouble(quake.getMagnitude()) < Double.parseDouble(highestMagEdit.getText().toString())){
                quakes.add(quake);
                quakes = addStats(quakes);
            }
        }


        displayToast(quakes);
    }

    /*
     * This method filters the earthquakes by depth
     */
    private void depthFilter(){

        EditText lowestDepthEdit = (EditText) search.getInputs()[0];
        EditText highestDepthEdit = (EditText) search.getInputs()[1];
        ArrayList<Quake> quakes = new ArrayList<Quake>();
        for(Quake quake : feedParser.getQuakes()){
            if(Double.parseDouble(quake.getDepth().split("\\s+")[0]) > Double.parseDouble(lowestDepthEdit.getText().toString()) && Double.parseDouble(quake.getDepth().split("\\s+")[0]) < Double.parseDouble(highestDepthEdit.getText().toString())){
                quakes.add(quake);
                quakes = addStats(quakes);
            }
        }

        displayToast(quakes);
    }

    /*
     * This filters the list by Location and displays only the earthquakes at that location
     */
    private void locationFilter(){

        Spinner locationSpinner = (Spinner) search.getInputs()[0];
        ArrayList<Quake> quakes = new ArrayList<>();
        for(Quake quake : feedParser.getQuakes()){
            if(quake.getLocation().equalsIgnoreCase(locationSpinner.getSelectedItem().toString())){
                quakes.add(quake);
                quakes = addStats(quakes);
            }
        }

            displayToast(quakes);
    }


    /*
     * This method gets the earthquakes between the two dates the user has input so that only the earthquakes that match the filter are displayed
     */
    private void timeFilter(){

        EditText startTime = (EditText) search.getInputs()[1];
        EditText endTime = (EditText) search.getInputs()[2];

        ArrayList<Quake> quakes = new ArrayList<>();

        for(Quake quake : feedParser.getQuakes()) {
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime.getText().toString().split(":")[0]));
            start.set(Calendar.MINUTE, Integer.valueOf(startTime.getText().toString().split(":")[1]));

            Calendar finish = Calendar.getInstance();
            finish.set(Calendar.HOUR_OF_DAY, Integer.valueOf(Integer.valueOf(endTime.getText().toString().split(":")[0])));
            finish.set(Calendar.MINUTE, Integer.valueOf(endTime.getText().toString().split(":")[1]));



            Calendar quakeTime = Calendar.getInstance();
            quakeTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(quake.getTime().split(":")[0]));
            quakeTime.set(Calendar.MINUTE, Integer.valueOf(quake.getTime().split(":")[1]));

            try{
                Date selectedDate = new SimpleDateFormat("dd MMM yyyy").parse(((EditText)search.getInputs()[0]).getText().toString());
                Date quakeDate = new SimpleDateFormat("dd MMM yyyy").parse(quake.getDate().substring(4,quake.getDate().length()));
                if(selectedDate.getTime() == quakeDate.getTime()){
                    if(quakeTime.after(start) && quakeTime.before(finish)){
                        quakes.add(quake);
                        quakes = addStats(quakes);
                    }
                }
            }catch (ParseException e){
                e.printStackTrace();
            }



        }
        displayToast(quakes);


    }

    private void displayToast(ArrayList<Quake> quakes){

        if(quakes.size() == 0){
            Toast.makeText(self,"No Earthquakes Matching Search Parameters",Toast.LENGTH_LONG).show();
        } else{
            new ListDisplay().displayAll(self, quakes);
        }
    }

    /*
     * This adds the most extreme tags to the earthquakes such as the most northern, or the deepest etc
     */
    private ArrayList<Quake> addStats(ArrayList<Quake> quakes){



        HashMap<String,Integer> stats = new HashMap<>();
        double maxtLatitude = -90;
        double minLongitude = 180;
        double maxLongitude = -180;
        double minLatitude = 90;
        double maxMagnitude = -10.00;
        double minDepth = 0;
        double maxDepth =  100;
        int i = 0;
        for(Quake quake: quakes){
            quake.setStat(null);
            double lat = Double.parseDouble(quake.getLatitude());
            double lon = Double.parseDouble(quake.getLongitude());
            double mag = Double.parseDouble(quake.getMagnitude());
            double depth = Double.parseDouble(quake.getDepth().split("\\s+")[0]);

            //Northern
            if(lat > maxtLatitude){
                maxtLatitude = lat;
                stats.put("MN",i);

            }

            //Eastern
            if(lon < minLongitude){
                minLongitude = lon;
                stats.put("ME",i);
            }

            //Southern
            if(lat < minLatitude){
                minLatitude = lat;
                stats.put("MS",i);
            }

            //Western
            if(lon > maxLongitude){
                maxLongitude = lon;
                stats.put("MW",i);
            }

            //Highest Magnitude
            if(mag > maxMagnitude){
                maxMagnitude = mag;
                stats.put("HM",i);
            }

            //Shallowest Depth
            if(depth < maxDepth){
                maxDepth = depth;
                stats.put("SE",i);
            }

            //Deepest depth
            if(depth > minDepth){
                minDepth = depth;
                stats.put("DE",i);

            }
            i++;
        }


        for(String key : stats.keySet()){
            int value = stats.get(key);
            Log.e("value",value + "");
            String  stat = quakes.get(value).getStat();
            if(stat == null){
                stat = key + ",";
            }else{
                stat = stat + "," + key;
            }
            stat = stat.replaceAll(",,",",");
            quakes.get(value).setStat(stat);
        }


        return  quakes;
    }



}
