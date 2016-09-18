package com.ludee.travelbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ludee on 29-08-2016.
 */

public class TripListAdapter extends ArrayAdapter<Trip> {
    Context context;
    int layoutResourceId;
    Trip data[] = null;

    public TripListAdapter(Context context, int layoutResourceId, Trip[] data){
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
            holder.id = (TextView) row.findViewById(R.id.tv_id);
            holder.dest = (TextView) row.findViewById(R.id.tv_dest);
            holder.date = (TextView) row.findViewById(R.id.tv_date);

            row.setTag(holder);
        }
        else
        {
            holder = (TripHolder)row.getTag();
        }

        Trip trip = data[position];
        holder.id.setText(""+trip.getId());
        holder.dest.setText(trip.getDest());
        holder.date.setText(trip.getDate());

        return row;
    }

    static class TripHolder
    {
        TextView id;
        TextView dest;
        TextView date;
    }
}
