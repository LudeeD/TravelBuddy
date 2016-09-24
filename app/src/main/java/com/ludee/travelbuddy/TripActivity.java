package com.ludee.travelbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ludee on 27-08-2016.
 */
public class TripActivity extends AppCompatActivity{

    DataBaseHandler db = new DataBaseHandler(this);
    private ArrayList<Item> items;
    private Trip t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main);
        Intent intent = getIntent();

        String dest = intent.getStringExtra("dest");
        t = db.getTrip("\""+dest+"\"");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_trip);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Novo Item");
                builder.setView(edittext);
                builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String new_item = edittext.getText().toString();
                        items.add(new Item(new_item));
                        t.updateItems(items);
                        db.updateTrip(t);
                        updateUi();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("Item","Canceled");
                    }
                });

                builder.show();


            }

        });

        updateUi();

    }

    @Override
    public void onBackPressed() {
        t.updateItems(items);
        db.updateTrip(t);
        Intent comeback = new Intent();
        setResult(2,comeback);
        finish();
    }

    private void updateUi(){
        Log.d("Updating","Trip_Edit");
        items = new ArrayList<Item>();

        TextView tv = (TextView) findViewById(R.id.textView_dest);
        tv.setText(t.getDest());
        tv = (TextView) findViewById(R.id.textView_date);
        tv.setText(t.getDate());

        items = t.getItems_items();
        Log.d("Items",t.getItems_string());

        ItemListAdapter adapter = new ItemListAdapter(this,R.layout.list_items_layout,items.toArray(new Item[items.size()]));
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }
}
