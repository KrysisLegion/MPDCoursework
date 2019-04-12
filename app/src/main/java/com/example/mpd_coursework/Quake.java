
package com.example.mpd_coursework;

import android.util.Log;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


public class Quake {

//  variables needed for the Quake class
    private String Magnitude;
    private String Latitude;
    private String Longitude;
    private String Location;
    private String Depth;
    private String Date;
    private String Time;
    private String stat;

//  Getters & Setters for all the above variables
    public String getMagnitude() {
        return Magnitude;
    }

    public void setMagnitude(String magnitude) {
        Magnitude = magnitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String aLongitude) {
        Longitude = aLongitude;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDepth() {
        return Depth;
    }

    public void setDepth(String depth) {
        Depth = depth;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

//  This parses the description tag in the RSS feed and splits the information up as required for each of the variables
    public void parse(String description){
        String[] info = description.split(";");
        int x = 0;
        for(String data : info){
            String[] item = data.split(":\\s");

            if(x == 0) {
                //Date and time of the earthquake:
                String date = item[1].substring(0, 17).trim();
                setDate(date);
                String time = item[1].substring(17, 22).trim();
                setTime(time);
            }
            if (x==1) {
                //Location of the earthquake:
                setLocation(item[1]);
            }
            if (x==2) {
                //Coordinates of the earthquake:
                String[] Coordinates = item[1].split(",");
                setLatitude(Coordinates[0].trim());
                setLongitude(Coordinates[1].trim());
            }
            if (x==3) {
                //Depth of the earthquake:
                setDepth(item[1].trim());
            }
            if (x==4) {
                //Magnitude of the earthquake:
                setMagnitude(item[1].trim());
            }

            x++;
        }
    }



}