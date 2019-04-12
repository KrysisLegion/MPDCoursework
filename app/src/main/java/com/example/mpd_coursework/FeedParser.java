package com.example.mpd_coursework;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */

public class FeedParser extends AsyncTask<Void,Void,Void> {

    private Context context;
    private ArrayList<Quake> quakes = new ArrayList<Quake>();


    public FeedParser(Context context){

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        new ListDisplay().displayAll(context,quakes);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXML();
        final MainActivity main = (MainActivity) this.context;
        final FeedParser feedParser = this;
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main.onReady(feedParser);
            }
        });
        return null;
    }

    /*
     *  This class gets every item from the XML document with the <description> tag on it and sends it to the Quake class.
     *  In the Quake class the description tag is broken down using substrings to get all the necessary information needed.
     *  Once the information is set correctly for the variables in the Quake class an arraylist of the quake class is created which is used for displaying the earthquakes on the screen
     */
    private void ProcessXML() {
        try {
            URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            parseFeed(in);
            in.close();
        } catch (IOException ae) {
            ae.printStackTrace();
        }

    }

    public ArrayList<Quake> getQuakes() {
        return this.quakes;
    }

    public void parseFeed(BufferedReader in) throws IOException {
        String cLine ;
        boolean item = false;
        while ((cLine = in.readLine()) != null) {
            if (cLine.contains("<item>")) {
                item = true;
            } else if (cLine.contains("<description>") && item == true) {
                Quake earthquake = new Quake();

                cLine = cLine.replace("<description>", "");
                cLine = cLine.replace("</description>", "");

                earthquake.parse(cLine);
                quakes.add(earthquake);
            }
        }
    }

}

