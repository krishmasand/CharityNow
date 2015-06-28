package com.charitynow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.Arrays;


public class OrganizationListActivity extends AppCompatActivity {
    private static String TAG = "OrganizationListActivity";
    OrganizationCustomAdapter adapter;
    ListView lv;
    RetrofitClient mRC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_list);
        lv = (ListView) findViewById(R.id.listView2);
        mRC = new RetrofitClient();
        //mRC.getPlaces(this);
        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase("https://brilliant-torch-3400.firebaseio.com/");

    }

    public void setupLV(){

        String[] companiesStrings = new String[Data.companies.size()];
        for(int i = 0; i < companiesStrings.length; i++){
            companiesStrings[i] = Data.companies.get(i).name;
        }

        Arrays.sort(companiesStrings);
        adapter = new OrganizationCustomAdapter(this, companiesStrings);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_organization_list, menu);
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
