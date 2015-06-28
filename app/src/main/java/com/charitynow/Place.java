package com.charitynow;

/**
 * Created by Rithmio on 6/28/15.
 */
public class Place {
    public String name;
    public float lon;
    public float lat;
    public int trafficScore;

    public Place(String n, float l1, float l2, int tS){
        name = n;
        lon = l1;
        lat = l2;
        trafficScore = tS;
    }

    public Place(Place otherPlace)
    {
        this.name = otherPlace.name;
        this.lon = otherPlace.lon;
        this.lat = otherPlace.lat;
        this.trafficScore = otherPlace.trafficScore;
    }

}
