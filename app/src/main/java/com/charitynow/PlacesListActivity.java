package com.charitynow;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;



public class PlacesListActivity extends Activity {
    PlacesCustomAdapter adapter;
    ListView lv;
    RetrofitClient mRC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        lv = (ListView) findViewById(R.id.listView);
        mRC = new RetrofitClient();
        mRC.getPlaces(this);
        Firebase.setAndroidContext(this);



    }

    public void setupLV(){

        Collections.sort(Data.places, new CustomComparator());

        String[] placesStrings = new String[Data.places.size()];
        for(int i = 0; i < placesStrings.length; i++){
            placesStrings[i] = Data.places.get(i).name;
        }

        int [] trafficScores = new int[Data.places.size()];
        for(int i = 0; i < placesStrings.length; i++){
            trafficScores[i] = Data.places.get(i).trafficScore;
        }
        adapter = new PlacesCustomAdapter(this, placesStrings, trafficScores);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomComparator implements Comparator<Place> {
        @Override
        public int compare(Place o1, Place o2) {
            if( o1.trafficScore < o2.trafficScore) return 1;
            else if( o1.trafficScore == o2.trafficScore) return 0;
            else return -1;
        }
    }
}


