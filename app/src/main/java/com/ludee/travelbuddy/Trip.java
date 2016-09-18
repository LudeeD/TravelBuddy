package com.ludee.travelbuddy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ludee on 27-08-2016.
 */
public class Trip {
    private long id;
    private String dest;
    private String date;
    private ArrayList<Item> items;


    Trip(long id, String dst, String dt){
        this.id = id;
        this.dest = dst;
        this.date = dt;
        items = new ArrayList<>();
    }

    Trip(long id, String dst, String dt, String i){
        this.id = id;
        this.dest = dst;
        this.date = dt;
        items = new ArrayList<>();
        if(i.startsWith("e")){
            Log.d("Items","Empty");
        }else {
            String[] items = i.split("-");

            for (int j = 0; j < items.length; j++) {
                boolean c;
                String[] it_name_check = items[j].split("_");
                if(it_name_check[1].startsWith("f")) c = false;
                else c = true;
                Item madik = new Item(it_name_check[0],c);
                this.items.add(madik);
            }
        }
    }

    @Override
    public String toString(){
        return ""+id+". "+this.dest+" ("+this.date+")"+getItems_string();
    }

    public long getId() {
        return id;
    }

    public void setDest(String d) {this.dest = d;}
    public void setDate(String d) {this.dest = d;}

    public String getDest() {
        return dest;
    }

    public String getDate() {
        return date;
    }

    public String getItems_string() {
        String r="";
        if(items.size()==0) return r = "e";

        for (int i = 0; i<items.size();i++) {
            r = r + items.get(i).getName() + "_" + items.get(i).getCheckStatus() + "-";
        }
        return r;
    }

    public ArrayList<Item> getItems_items(){
        return this.items;
    }

    public void updateItems(ArrayList<Item> items){
        this.items = items;
    }

    public void addItem(Item i){
        this.items.add(i);
    }
}


class Item{
    private String name;
    private boolean check;

    Item(String n){
        this.name = n;
        this.check = false;
    }
    Item(String n, boolean c){
        this.name = n;
        this.check = c;
    }
    Item(Item i){
        this.name = i.getName();
        this.check = i.getCheckStatus();
    }

    public void check(){
        if(getCheckStatus()) this.check = false;
        else this.check = true;
    }

    public String getName() {
        return this.name;
    }

    public boolean getCheckStatus(){
        return this.check;
    }
}
