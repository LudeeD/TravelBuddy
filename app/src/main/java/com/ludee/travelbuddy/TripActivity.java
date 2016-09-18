package com.ludee.travelbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main);
        Intent intent = getIntent();

        String dest = intent.getStringExtra("dest");
        t = db.getTrip("\""+dest+"\"");

        srl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_item);
        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("LOG_TAG", "onRefresh called from SwipeRefreshLayout items");
                        updateUi();
                        t.updateItems(items);
                        db.updateTrip(t);
                    }
                }
        );

        updateUi();

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
        srl.setRefreshing(false);
    }
}
