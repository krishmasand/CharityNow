package com.charitynow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class ChooseActivity extends AppCompatActivity {
    RetrofitClient mRC;
    EditText mET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        mET = (EditText) findViewById(R.id.editText);
       // Firebase.setAndroidContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
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

    public void startOrganizationActivity(View view){
        if(mET.getText().toString().equals("")){
            Toast.makeText(this, "Please enter an organization name", Toast.LENGTH_LONG).show();
        }
        else{
            Data.companyName = mET.getText().toString();
            startActivity(new Intent(getApplicationContext(), PlacesListActivity.class));
        }
    }

    public void startUserActivity(View view){
        startActivity(new Intent(getApplicationContext(), OrganizationListActivity.class));
    }
}
