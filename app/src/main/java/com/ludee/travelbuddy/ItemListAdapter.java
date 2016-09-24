package com.ludee.travelbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by ludee on 29-08-2016.
 */

public class ItemListAdapter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    Item data[] = null;

    public ItemListAdapter(Context context, int layoutResourceId, Item[] data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TripHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TripHolder();
            holder.item_name = (TextView) row.findViewById(R.id.textView);
            holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
            row.setTag(holder);
        }
        else
        {
            holder = (TripHolder)row.getTag();
        }

        final Item item = data[position];
        holder.item_name.setText(item.getName());
        holder.checkBox.setChecked(item.getCheckStatus());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.check();
            }
        });
        return row;
    }

    static class TripHolder
    {
        TextView item_name;
        CheckBox checkBox;
    }
}
