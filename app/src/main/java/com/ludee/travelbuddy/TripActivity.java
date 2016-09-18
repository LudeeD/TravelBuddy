package com.ludee.travelbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ludee on 27-08-2016.
 */
public class TripActivity extends AppCompatActivity{

    DataBaseHandler db = new DataBaseHandler(this);
    private ArrayList<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main);
        Intent intent = getIntent();

        String dest = intent.getStringExtra("dest");
        Trip t = db.getTrip("\""+dest+"\"");
        TextView tv = (TextView) findViewById(R.id.textView_dest);
        tv.setText(t.getDest());
        tv = (TextView) findViewById(R.id.textView_date);
        tv.setText(t.getDate());
        ArrayList<Item> items = t.getItems_items();




    }
}
