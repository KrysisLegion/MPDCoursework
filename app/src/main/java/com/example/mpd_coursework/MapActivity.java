package com.example.mpd_coursework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


/*
 * This class displays the information on the earthquake into TextViews
 */
public class MapActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        TextView date = this.findViewById(R.id.dateTxt);
        TextView time = this.findViewById(R.id.timeTxt);
        TextView depth = this.findViewById(R.id.depthTxt);
        TextView mag = this.findViewById(R.id.magnitudeTxt);
        TextView loc = this.findViewById(R.id.locationTxt);

        Bundle b = getIntent().getExtras();


        loc.setText(b.getString("Location"));
        date.setText(b.getString("Date"));
        time.setText(b.getString("Time"));
        depth.setText(b.getString("Depth"));
        mag.setText(b.getString("Magnitude"));

        final String Lat = b.getString("Latitude");
        final String Long = b.getString("Longitude");

            //this displays the MapView on the screen
            final MapView mapView = this.findViewById(R.id.mapView);

            mapView.onCreate(this.getIntent().getExtras());

            mapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.clear();
                    LatLng point = new LatLng(Double.parseDouble(Lat),Double.parseDouble(Long));
                    googleMap.addMarker(new MarkerOptions().position(point));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
                    mapView.onResume();
                }
            });

    }
}
