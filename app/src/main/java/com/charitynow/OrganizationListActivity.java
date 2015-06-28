package com.charitynow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class OrganizationListActivity extends Activity {
    private static String TAG = "OrganizationListActivity";
    OrganizationCustomAdapter adapter;
    ListView lv;
    RetrofitClient mRC;

    @TargetApi(21)
    public void setStatusBar(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#303F9F")); //slightly darker than action bar
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data.companies = new HashSet<>();
        lv = (ListView) findViewById(R.id.listView2);
        mRC = new RetrofitClient();
        //mRC.getPlaces(this);
        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase("https://brilliant-torch-3400.firebaseio.com/");
        Log.d(TAG, firebase.toString());
        Log.d(TAG, firebase.getRoot().toString());
        Query queryRef = firebase.orderByChild("name");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                boolean add = true;
                try {
                    Company newComp = new Company(snapshot.getKey(), new Place((String) value.get("name"),
                            Float.parseFloat(value.get("lat").toString()),
                            Float.parseFloat(value.get("lon").toString()),
                            Integer.parseInt(value.get("trafficScore").toString())));
                    for(Company c : Data.companies){
                        if (c.name.equals(newComp.name)){
                            add = false;
                            break;
                        }
                    }
                    if(add) Data.companies.add(newComp);
                }
                catch(Exception e){}
                setupLV();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            // ....
        });
        queryRef.startAt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_list);
        lv = (ListView) findViewById(R.id.listView2);
        if (Build.VERSION.SDK_INT >= 21) setStatusBar();
        mRC = new RetrofitClient();
        //mRC.getPlaces(this);
        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase("https://brilliant-torch-3400.firebaseio.com/");
        Log.d(TAG, firebase.toString());
        Log.d(TAG, firebase.getRoot().toString());
        Query queryRef = firebase.orderByChild("name");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                try {
                    Company newComp = new Company(snapshot.getKey(), new Place((String) value.get("name"),
                            Float.parseFloat(value.get("lat").toString()),
                            Float.parseFloat(value.get("lon").toString()),
                            Integer.parseInt(value.get("trafficScore").toString())));
                    Data.companies.add(newComp);
                }
                catch(Exception e){}
                setupLV();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            // ....
        });
        queryRef.startAt();

    }

    public void setupLV(){
        Data.companiesArrayList = new ArrayList<>();
        for(Company c : Data.companies){
            Data.companiesArrayList.add(c);
        }
        String[] companiesStrings = new String[Data.companiesArrayList.size()];
        Log.d(TAG, Data.companiesArrayList.size() + "");
        for(int i = 0; i < companiesStrings.length; i++){
            companiesStrings[i] = Data.companiesArrayList.get(i).name;
        }
        String[] locationStrings = new String[Data.companies.size()];
        for(int i = 0; i < companiesStrings.length; i++){
            locationStrings[i] = Data.companiesArrayList.get(i).checkedInPlace.name;
        }


        //Arrays.sort(companiesStrings);
        adapter = new OrganizationCustomAdapter(this, companiesStrings, locationStrings);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_organization_list, menu);
        return false;
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
