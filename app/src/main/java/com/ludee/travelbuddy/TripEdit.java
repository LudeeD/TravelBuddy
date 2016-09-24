package com.ludee.travelbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ludee on 29-08-2016.
 */
public class TripEdit extends AppCompatActivity {
    ArrayList<Item> items;
    DataBaseHandler db = new DataBaseHandler(this);
    Trip t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_edit);

        Intent intent = getIntent();
        String dest = intent.getStringExtra("dest");
        Log.d("Edit","Retrieving Trip");
        t = db.getTrip("\""+dest+"\"");
        Log.d("Trip_ITems",t.getItems_string());

        updateUi();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_trip_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Remover Viagem");
                builder.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteTrip(t);
                        Intent comeback = new Intent();
                        setResult(3,comeback);
                        finish();
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

    }

    @Override
    public void onBackPressed() {
        t.updateItems(items);
        db.updateTrip(t);
        Intent comeback = new Intent();
        setResult(3,comeback);
        finish();
    }

    private void updateUi() {
        Log.d("Updating", "Trip_Edit");
        items = new ArrayList<Item>();

        TextView tv1 = (TextView) findViewById(R.id.textView_dest_edit);
        tv1.setText(t.getDest());
        TextView tv2 = (TextView) findViewById(R.id.textView_date_edit);
        tv2.setText(t.getDate());

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Novo Nome");
                builder.setView(edittext);
                builder.setPositiveButton("Concluir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String new_name = edittext.getText().toString();
                        t.setDest(new_name);
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

        items = t.getItems_items();
        Log.d("Items", t.getItems_string());

        ItemEditListAdapter adapter = new ItemEditListAdapter(this, R.layout.list_items_edit_layout, items.toArray(new Item[items.size()]));
        ListView listView = (ListView) findViewById(R.id.listView_items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), TripEdit.class);
                String selected = ((TextView) view.findViewById(R.id.item_del)).getText().toString();
                Item lol = null;
                for (Item it : items) {
                    if (it.getName() == selected) {
                        lol = it;
                        break;
                    }
                }

                items.remove(lol);
                t.updateItems(items);
                db.updateTrip(t);
                intent.putExtra("dest", t.getDest());
                startActivity(intent);
                finish();
            }
        });
    }
}