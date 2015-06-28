package com.charitynow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class ChooseActivity extends Activity {
    RetrofitClient mRC;
    EditText mET;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        mET = (EditText) findViewById(R.id.editText);
        if (!LoadValue("companyName").equals("")){
            mET.setText(LoadValue("companyName"));
        }
        if (Build.VERSION.SDK_INT >= 21) setStatusBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
        return true;
    }

    @TargetApi(21)
    public void setStatusBar(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#303F9F")); //slightly darker than action bar
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
            SetValue("companyName", mET.getText().toString());
            startActivity(new Intent(getApplicationContext(), PlacesListActivity.class));
        }
    }

    public void startUserActivity(View view){
        startActivity(new Intent(getApplicationContext(), OrganizationListActivity.class));
    }

    public void SetValue(String s, String s2){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(s, s2);
        editor.commit();
    }

    public String LoadValue(String s){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(s, "");
    }
}
