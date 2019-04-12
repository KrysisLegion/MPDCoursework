package com.example.mpd_coursework;

/*
 *  Kristopher O'Rourke
 *  S1709870
 *  KOROUR203
 */

/*
 * This stores the search information when passing it throughout the application
 */
public class Search {
    private Object[] inputs;
    private String type;

    public Search(Object[] inputs, String type) {
        this.inputs = inputs;
        this.type = type;
    }

    public Object[] getInputs() {
        return inputs;
    }

    public void setInputs(Object[] inputs) {
        this.inputs = inputs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
