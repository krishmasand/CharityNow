package com.charitynow;

/**
 * Created by Radhir on 6/28/15.
 */
import com.charitynow.Place;
public class Company
{

    private String name;
    private Place checkedInPlace;

    public Company(String name, Place checkedInPlace)
    {
        this.name = name;
        this.checkedInPlace = new Place(checkedInPlace);
    }

    public String getName()
    {
        return name;
    }

    public Place getCheckedInPlace()
    {
        return checkedInPlace;
    }

}
