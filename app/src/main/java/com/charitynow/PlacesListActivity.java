package com.charitynow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.Arrays;


public class PlacesListActivity extends ActionBarActivity {
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
        
        String[] placesStrings = new String[Data.places.size()];
        for(int i = 0; i < placesStrings.length; i++){
            placesStrings[i] = Data.places.get(i).name;
        }

//        if(placesStrings.length <= 0){
//            title = (TextView) findViewById(R.id.textView);
//            if(bluetoothAdapter == null) title.setText("Bluetooth is not available");
//            else if(!bluetoothAdapter.isEnabled()) title.setText("Bluetooth is not enabled");
//            else title.setText("No Bluetooth Devices Found");
//        }
//        else{
//            title = (TextView) findViewById(R.id.textView);
//            title.setText("Devices");
//        }
        Arrays.sort(placesStrings);
        adapter = new PlacesCustomAdapter(this, placesStrings);
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
}
