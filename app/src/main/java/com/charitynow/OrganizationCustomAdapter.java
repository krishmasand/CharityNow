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

public class OrganizationCustomAdapter extends ArrayAdapter<String>{
    static Toast toast;
    private static String TAG = "OrganizationCustomAdapter";
    SharedPreferences sharedPreferences;
    String[] strings = null;
    //        BroadcastReceiver[] removeReceivers;
    String[] subtexts = null;
    String currentString;
    Context context;
    ViewGroup p;
    Place place;

    public OrganizationCustomAdapter(Context context, String[] resource, String[] subtexts) {
        super(context,R.layout.row,resource);
        this.context = context;
        this.strings = resource;
        this.subtexts = subtexts;
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

                    }
                });
                Button checkInButton = (Button) dialog.findViewById(R.id.button4);
                checkInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();
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

        iv.setImageResource(R.mipmap.sleeping);
        sub.setText(subtexts[position]);
        relativeLayout.setBackgroundColor(Color.WHITE);
        name.setTextColor(Color.BLACK);
        sub.setTextColor(Color.BLACK);

        return convertView;
    }



}
