package com.ludee.travelbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ludee on 27-08-2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "tripsManager";

    // Contacts table name
    private static final String TABLE_TRIPS = "trip_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TRIP_NAME = "trip";
    private static final String KEY_TRIP_DATE = "date";
    private static final String KEY_TRIP_ITEMS = "items";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TRIPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TRIP_NAME + " TEXT,"
                + KEY_TRIP_DATE + " TEXT,"
                + KEY_TRIP_ITEMS + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);

        // Create tables again
        onCreate(db);
    }

    public void addContact(Trip trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_NAME, trip.getDest()); // Trip Destination
        values.put(KEY_TRIP_DATE, trip.getDate()); // Trip Date
        values.put(KEY_TRIP_ITEMS, trip.getItems()); //Trip Items
        // Inserting Row
        db.insert(TABLE_TRIPS, null, values);
        db.close(); // Closing database connection
    }

    public Trip getTrip(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,trip,date,items FROM trip_table WHERE id="+id, null);
        String dest="Error";
        String date="Error";
        String items="Error";
        if(c.moveToFirst()){
            do{
                //assing values
                dest = c.getString(1);
                date = c.getString(2);
                items = c.getString(3);
                //Do something Here with values

            }while(c.moveToNext());
        }
        c.close();
        db.close();

        //Trip trip = new Trip(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return new Trip(id,dest,date,items);
    }

    public ArrayList<Trip> getTrips(){
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        // Select All Query
        String selectQuery = "SELECT  id,trip,date FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trip t = new Trip(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
                tripList.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return tripList;
    }

    public int updateTrip(Trip t) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_NAME, t.getDest());
        values.put(KEY_TRIP_DATE, t.getDate());
        values.put(KEY_TRIP_ITEMS, t.getItems());

        // updating row
        return db.update(TABLE_TRIPS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(t.getId()) });
    }

    public void deleteTrip(Trip t) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIPS, KEY_ID + " = ?",
                new String[] { String.valueOf(t.getId()) });
        db.close();
    }
}