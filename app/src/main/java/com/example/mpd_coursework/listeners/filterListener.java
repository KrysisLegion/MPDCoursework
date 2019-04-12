package com.example.mpd_coursework.listeners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mpd_coursework.FeedParser;
import com.example.mpd_coursework.ListDisplay;
import com.example.mpd_coursework.Quake;
import com.example.mpd_coursework.R;
import com.example.mpd_coursework.Search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


/*
 *   This class implements the filters into the application so once a filter is chosen from the Filter Spinner
 *   the input areas are displayed allowing for a filter to be queried on the RSS Feed
 */

public class filterListener implements AdapterView.OnItemSelectedListener {
    private Activity self;
    private FeedParser feedParser;

    public filterListener(Activity self, FeedParser feedParser) {
        this.self = self;
        this.feedParser = feedParser;
    }

    public filterListener(Activity self) {
        this.self = self;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*
         * This resets all of the layouts for query parameters when a new filter is selected
         */
        LinearLayout inputs1 = self.findViewById(R.id.filterContentInputs1);
        LinearLayout inputs2 = self.findViewById(R.id.filterContentInputs2);
        LinearLayout inputs3 = self.findViewById(R.id.filterContentInputs3);
        LinearLayout inputs4 = self.findViewById(R.id.filterContentInputs4);
        LinearLayout statInputs = self.findViewById(R.id.filterContentStats);
        inputs1.removeAllViews();
        inputs2.removeAllViews();
        inputs3.removeAllViews();
        statInputs.removeAllViews();


        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setColor(Color.parseColor("#00695A"));
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setStrokeWidth(3);

        /*
         *
         *  The functions here are the logic behind each of the filter options
         *  When no filter is selected or the Display All spinner item is chosen all entries into the RSS Feed Are displayed
         */
        if(parent.getSelectedItemPosition() == 0){

            new ListDisplay().displayAll(self,feedParser.getQuakes());

        }else{

            String filter = parent.getSelectedItem().toString();

            /*
             *  The following is the logic behind displaying the Time filter parameters and performing the query.
             *  This filter will display all the earthqfuakes on a specific date between two Time parameters
             */

            if(filter == "Time"){

                //Start Date Label
                TextView startDate = new TextView(self);
                startDate.setText("Start Date");
                startDate.setTextSize(16);
                startDate.setGravity(Gravity.CENTER);
                startDate = (TextView) ChangeWeight(startDate,50);

                inputs1.addView(startDate, 0);

                //Start Date Input
                EditText startEditText = new EditText(self);
                startEditText.setFocusableInTouchMode(false);
                startEditText.setBackground(border);
                startEditText = (EditText) ChangeWeight(startEditText, 50);
                startEditText.setOnClickListener(new dateInput(startEditText, self));
                inputs2.addView(startEditText, 0);

                //Starting Time Label
                TextView startTime = new TextView(self);
                startTime.setText("Start Time");
                startTime.setTextSize(16);
                startTime.setGravity(Gravity.CENTER);
                startTime = (TextView) ChangeWeight(startTime,50);

                inputs1.addView(startTime, 1);

                //Starting Time Input
                EditText startTimeEditText = new EditText(self);
                startTimeEditText.setFocusableInTouchMode(false);
                startTimeEditText.setBackground(border);
                startTimeEditText = (EditText) ChangeWeight(startTimeEditText, 50);
                startTimeEditText.setOnClickListener(new timeInput(self,startTimeEditText));

                inputs2.addView(startTimeEditText, 1);


                //End Time Label
                TextView endTime = new TextView(self);
                endTime.setText("End Time");
                endTime.setTextSize(16);
                endTime.setGravity(Gravity.CENTER);
                endTime = (TextView) ChangeWeight(endTime,50);

                inputs1.addView(endTime, 2);

                //End Time Input
                EditText endTimeEditText = new EditText(self);
                endTimeEditText.setFocusableInTouchMode(false);
                endTimeEditText.setBackground(border);
                endTimeEditText = (EditText) ChangeWeight(endTimeEditText, 50);
                endTimeEditText.setOnClickListener(new timeInput(self,endTimeEditText));
                inputs2.addView(endTimeEditText, 2);

                //Button to search this query
                Button submitBtn = new Button(self);
                submitBtn.setText("Search");
                submitBtn = (Button) ChangeWeight(submitBtn, 100);
                submitBtn.setOnClickListener(new searchInput(feedParser, self, new Search(new Object[]{startEditText,startTimeEditText,endTimeEditText},"Time")));
                inputs3.addView(submitBtn, 0);

            }else if(filter == "Date"){
                /*
                 *  The following is the logic behind displaying the Date filter parameters and performing the query.
                 */

                //This Displays the TextView label for the Start Date of the search
                TextView startDate = new TextView(self);
                startDate.setText("Start Date");
                startDate.setTextSize(16);
                startDate.setGravity(Gravity.CENTER);
                startDate = (TextView) ChangeWeight(startDate,50);
                inputs1.addView(startDate, 0);

                //This creates and displays the input field for the Start Date parameter of the query
                EditText startEdit = new EditText(self);
                startEdit.setFocusableInTouchMode(false);
                startEdit.setBackground(border);
                startEdit = (EditText) ChangeWeight(startEdit, 50);
                startEdit.setOnClickListener(new dateInput(startEdit, self));
                inputs2.addView(startEdit, 0);

                //This Displays the TextView and EditText for the "end date" parameter of the query.
                TextView endDate = new TextView(self);
                endDate.setText("End Date");
                endDate.setTextSize(16);
                endDate.setGravity(Gravity.CENTER);
                endDate = (TextView) ChangeWeight(endDate, 50);
                inputs1.addView(endDate, 1);


                EditText endEdit = new EditText(self);
                endEdit.setFocusableInTouchMode(false);
                endEdit.setBackground(border);
                endEdit = (EditText) ChangeWeight(endEdit, 50);
                endEdit.setOnClickListener(new dateInput(endEdit, self));
                inputs2.addView(endEdit, 1);

                /*
                 *This Displays the search button to perform they query on the RSS Feed.
                 *When pressed only the earthquakes matching the search parameters will be displayed on the RSS Feed.
                 *if none are found a message will be displayed and all earthquakes will be shown as normal.
                 */
                Button submitBtn = new Button(self);
                submitBtn.setText("Search");
                submitBtn = (Button) ChangeWeight(submitBtn, 100);
                submitBtn.setOnClickListener(new searchInput(feedParser, self, new Search(new Object[]{startEdit,endEdit},"Date")));
                inputs3.addView(submitBtn, 0);

            }else  if(filter == "Magnitude"){
                //This displays the input fields for the Magnitude filter when pressed
                TextView startMag = new TextView(self);
                startMag.setText("Start Magnitude");
                startMag.setTextSize(16);
                startMag.setGravity(Gravity.CENTER);
                startMag = (TextView) ChangeWeight(startMag,50);

                inputs1.addView(startMag, 0);

                EditText magEdit = new EditText(self);
                magEdit.setBackground(border);
                magEdit = (EditText) ChangeWeight(magEdit, 50);
                magEdit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                magEdit.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));

                inputs2.addView(magEdit, 0);

                TextView finalMag = new TextView(self);
                finalMag.setText("Final Magnitude");
                finalMag.setTextSize(16);
                finalMag.setGravity(Gravity.CENTER);
                finalMag = (TextView) ChangeWeight(finalMag,50);

                inputs1.addView(finalMag, 1);

                EditText finalMagEdit = new EditText(self);
                finalMagEdit.setBackground(border);
                finalMagEdit = (EditText) ChangeWeight(finalMagEdit, 50);
                finalMagEdit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                finalMagEdit.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
                inputs2.addView(finalMagEdit, 1);

                Button submitBtn = new Button(self);
                submitBtn.setText("Search");
                submitBtn = (Button) ChangeWeight(submitBtn, 100);
                submitBtn.setOnClickListener(new searchInput(feedParser, self, new Search(new Object[]{magEdit,finalMagEdit},"Magnitude")));
                inputs3.addView(submitBtn, 0);

            } else if(filter == "Location"){

                //This displays the filter for the Location component
                TextView finalLoc = new TextView(self);
                finalLoc.setText("Location");
                finalLoc.setTextSize(16);
                finalLoc.setGravity(Gravity.CENTER);
                finalLoc = (TextView) ChangeWeight(finalLoc,50);

                inputs1.addView(finalLoc, 0);

                Spinner locSpinner = new Spinner(self);

                ArrayList<String> locations = new ArrayList<>();
                for(Quake e : feedParser.getQuakes()){
                    if(!locations.contains(e.getLocation())) locations.add(e.getLocation());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(self,android.R.layout.simple_spinner_item, locations.toArray(new String[locations.size()]));
                locSpinner.setAdapter(adapter);
                locSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                locSpinner = (Spinner) ChangeWeight(locSpinner,100);
                locSpinner.setMinimumHeight(200);
                inputs2.addView(locSpinner,0);

                Button submitBtn = new Button(self);
                submitBtn.setText("Search");
                submitBtn = (Button) ChangeWeight(submitBtn, 100);
                submitBtn.setOnClickListener(new searchInput(feedParser, self, new Search(new Object[]{locSpinner},"Location")));
                inputs3.addView(submitBtn, 0);
            }
            else if(filter == "Depth"){

                //this displays the depth component of the filter
                TextView startDepth = new TextView(self);
                startDepth.setText("Start Depth");
                startDepth.setTextSize(16);
                startDepth.setGravity(Gravity.CENTER);
                startDepth = (TextView) ChangeWeight(startDepth,50);

                inputs1.addView(startDepth, 0);

                EditText depthEdit = new EditText(self);
                depthEdit.setBackground(border);
                depthEdit = (EditText) ChangeWeight(depthEdit, 50);
                depthEdit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                depthEdit.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

                inputs2.addView(depthEdit, 0);

                TextView finalDepth = new TextView(self);
                finalDepth.setText("Final Depth");
                finalDepth.setTextSize(16);
                finalDepth.setGravity(Gravity.CENTER);
                finalDepth = (TextView) ChangeWeight(finalDepth,50);

                inputs1.addView(finalDepth, 1);

                EditText finalDepthEdit = new EditText(self);
                finalDepthEdit.setBackground(border);
                finalDepthEdit = (EditText) ChangeWeight(finalDepthEdit, 50);
                finalDepthEdit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                finalDepthEdit.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
                inputs2.addView(finalDepthEdit, 1);

                Button submitBtn = new Button(self);
                submitBtn.setText("Search");
                submitBtn = (Button) ChangeWeight(submitBtn, 100);
                submitBtn.setOnClickListener(new searchInput(feedParser, self, new Search(new Object[]{depthEdit,finalDepthEdit},"Depth")));
                inputs3.addView(submitBtn, 0);

            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private View ChangeWeight(View view, int value){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = value;
        view.setLayoutParams(params);

        return view;
    }

    public DatePickerDialog displayDate(final EditText editText){
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(self, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                final Date startDate = calendar.getTime();
                String finaldate = dateFormat.format(startDate);

                editText.setText(finaldate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        return mDatePickerDialog;
    }

    protected TimePickerDialog displayTime(final EditText editText) {
        Calendar newCalendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(self, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(i < 10){
                    editText.setText("0"+i+":" + i1);
                }else{
                    editText.setText(i+":" + i1);
                }
            }

        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        return  timePickerDialog;

    }
}
