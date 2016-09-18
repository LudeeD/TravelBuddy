package com.ludee.travelbuddy;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ludee on 27-08-2016.
 */
public class New_Trip_Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newt_trip);

        Button bt = (Button) findViewById(R.id.saveInfo);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.editText);
                String dest = et.getText().toString();
                et = (EditText) findViewById(R.id.editText2);
                String date = et.getText().toString();
                Intent comeback = new Intent();
                comeback.putExtra("dest",dest);
                comeback.putExtra("date",date);
                setResult(2,comeback);
                finish();
            }
        });
    }
}
