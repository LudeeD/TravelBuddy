package com.ludee.travelbuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Trip> allTrips = new ArrayList<>();
    boolean changes = true;
    DataBaseHandler db = new DataBaseHandler(this);
    private int numberOfTrips = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Log.d("Insert:", "Inserting ..");
        numberOfTrips++;
        db.addContact(new Trip(numberOfTrips,"Porto","23/01/2015"));
        numberOfTrips++;
        db.addContact(new Trip(numberOfTrips,"Aveiro","aad"));
        ArrayList<Trip> tripList = db.getTrips();
        for (Trip t:tripList) {
            Log.d("Id->",""+t.getId());
            Log.d("Dest->",t.getDest());
            db.deleteTrip(t);
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),New_Trip_Activity.class);
                startActivityForResult(intent,1);
            }

        });
        updateUI();
    }

    private void updateUI(){
        allTrips = new ArrayList<>();
        for (Trip t:db.getTrips()) {
            allTrips.add(t);
        }
        TripListAdapter adapter = new TripListAdapter(this,R.layout.list_trip_layout,allTrips.toArray(new Trip[allTrips.size()]));
        ListView listView = (ListView) findViewById(R.id.listView_trips);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(),TripActivity.class);
                String selected = ((TextView) view.findViewById(R.id.tv_dest)).getText().toString();
                Log.d("dest",selected);
                intent.putExtra("dest",selected);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1){
            String dest = data.getStringExtra("dest");
            String date = data.getStringExtra("date");
            numberOfTrips++;
            Trip t =  new Trip(numberOfTrips,dest,date);
            allTrips.add(t);
            db.addTrip(t);
            updateUI();
        }

        if(requestCode==2){
            updateUI();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            Intent intent = new Intent(this,Settings.class);
            startActivityForResult(intent,2);
            changes = true;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


