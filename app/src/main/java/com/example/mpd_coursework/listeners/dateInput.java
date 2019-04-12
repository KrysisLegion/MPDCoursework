package com.example.mpd_coursework.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */


    /*
       This class changes the text of the editText that was selected the date the user selected.
    */
public class dateInput implements View.OnClickListener {
    public EditText dateEditText;
    public Activity self;

    public dateInput(EditText editText, Activity self) {
        this.dateEditText = editText;
        this.self = self;
    }

    @Override
    public void onClick(View v) {
        filterListener FilterListener = new filterListener(self);
        FilterListener.displayDate(dateEditText).show();
    }


}
