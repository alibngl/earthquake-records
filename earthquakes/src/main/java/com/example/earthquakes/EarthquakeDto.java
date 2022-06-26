package com.example.earthquakes;


import java.util.Date;
//All the information requested about the earthquake
public class EarthquakeDto {
    private Date time;

    private String place;
//Does not work via on double or int data type. Variable changed to object.
    private Object magnitude;

    public String getPlace() {
        return place;
    }

    public Date getTime() {
        return time;
    }

    public Object getMagnitude() {
        return magnitude;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setMagnitude(Object magnitude) {
        this.magnitude = magnitude;
    }
}
