package com.charitynow;


/**
 * Custom Adapter for ListView
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashSet;

public class PlacesCustomAdapter extends ArrayAdapter<String>{
    static Toast toast;
    private static String TAG = "PlacesCustomAdapter";
    SharedPreferences sharedPreferences;
    String[] strings = null;
    int[] trafficScores = null;
    //        BroadcastReceiver[] removeReceivers;
    String currentString;
    Context context;
    ViewGroup p;
    Place place;

    public PlacesCustomAdapter(Context context, String[] resource, int[] tS) {
        super(context,R.layout.row,resource);
        this.context = context;
        this.strings = resource;
        trafficScores = tS;
//            removeReceivers = new BroadcastReceiver[resource.length];
        //unregister();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        final RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative);
        final TextView name = (TextView) convertView.findViewById(R.id.textView1);
        final TextView sub = (TextView) convertView.findViewById(R.id.list_subtitle);
        final ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
        p = parent;



        View.OnClickListener pressed= new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.place_dialog);

                dialog.setCancelable(true);
                dialog.setTitle(strings[position]);
                place = Data.places.get(position);
                Button dirButton = (Button) dialog.findViewById(R.id.button3);
                dirButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+place.lat+","+place.lon);
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+strings[position]);
                        //Uri gmmIntentUri = Uri.parse("google.navigation:q="+Data.places.get(position).name);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        context.startActivity(mapIntent);
                        dialog.cancel();
                    }
                });
                Button checkInButton = (Button) dialog.findViewById(R.id.button4);
                checkInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(Place pl : Data.places){
                            if(pl.name.equals(strings[position])){
                                place = pl;
                                break;
                            }
                        }
                        Firebase firebase = new Firebase("https://brilliant-torch-3400.firebaseio.com/");
                        firebase.child(Data.companyName).setValue(place);
                        dialog.cancel();
                        SetValue("checkedIn", strings[position]);
                        PlacesListActivity PLA = (PlacesListActivity) context;
                        PLA.setupLV();
                    }
                });
                dialog.show();

            }
        };
        View.OnLongClickListener longPressed= new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                return true;
            }
        };
        relativeLayout.setOnClickListener(pressed);
        relativeLayout.setOnLongClickListener(longPressed);
        name.setText(strings[position]);
        currentString = strings[position];
        if(LoadValue("checkedIn").equals(currentString)) {
            iv.setImageResource(R.mipmap.thumbsup);
            sub.setText("Checked In");
            relativeLayout.setBackgroundColor(Color.parseColor("#8fa5fc"));
            name.setTextColor(Color.WHITE);
            sub.setTextColor(Color.WHITE);
        }
        else {
            iv.setImageResource(R.mipmap.sleeping);
            sub.setText("Traffic Score: " + trafficScores[position]);
            relativeLayout.setBackgroundColor(Color.WHITE);
            name.setTextColor(Color.BLACK);
            sub.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    public void SetValue(String s, String s2){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(s, s2);
        editor.commit();
    }

    public String LoadValue(String s){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(s, "");
    }



}
