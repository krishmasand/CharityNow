package com.charitynow;

/**
 * Created by Rithmio on 6/28/15.
 */
public class Place {
    public String name;
    public float lon;
    public float lat;

    public Place(String n, float l1, float l2){
        name = n;
        lon = l1;
        lat = l2;
    }

    public Place(Place otherPlace)
    {
        this.name = otherPlace.name;
        this.lon = otherPlace.lon;
        this.lat = otherPlace.lat;
    }

}
