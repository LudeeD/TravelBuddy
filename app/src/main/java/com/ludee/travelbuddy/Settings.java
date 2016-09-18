package com.ludee.travelbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by ludee on 29-08-2016.
 */
public class Settings extends AppCompatActivity {
    DataBaseHandler db = new DataBaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button bt = (Button) findViewById(R.id.btErase);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Trip t:db.getTrips()) {
                    db.deleteTrip(t);
                }
            }
        });

        bt = (Button) findViewById(R.id.btSee);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for (Trip t:db.getTrips()) {
                    Log.d("AAAAAAAAAAAAAA",t.toString());
                }
            }
        });
    }
}
