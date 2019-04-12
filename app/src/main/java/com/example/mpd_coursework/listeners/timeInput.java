package com.example.mpd_coursework.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */

public class timeInput implements View.OnClickListener {

    public EditText editText;
    public Activity self;

    /**
     *
     * @param activity -  The activity of the view that is getting edited
     * @param editText -  The editText that is getting changed into a date picker.
     */
    public timeInput(Activity activity, EditText editText){
        this.self = activity;
        this.editText = editText;
    }

    /*
       This changes the text of the editText that was selected to the time that was selected.
    */
    @Override
    public void onClick(View view) {
        filterListener FilterListener = new filterListener(self);
        FilterListener.displayTime(editText).show();
    }
}
