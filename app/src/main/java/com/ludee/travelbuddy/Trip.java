package com.ludee.travelbuddy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ludee on 27-08-2016.
 */
public class Trip {
    private int id;
    private String dest;
    private String date;
    private ArrayList<Item> items;


    Trip(int id, String dst, String dt){
        this.id = id;
        this.dest = dst;
        this.date = dt;
        items = new ArrayList<>();
    }

    Trip(int id, String dst, String dt, String i){
        this.id = id;
        this.dest = dst;
        this.date = dt;
        items = new ArrayList<>();
        if(i.contains("Error")){
            this.items.add(new Item("Empty"));
        }else {
            String[] items = i.split("_");

            for (int j = 0; j < items.length; j++) {
                String[] it_name_check = items[j].split("-");
                Item madik = new Item(it_name_check[0]);

                this.items.add(madik);
            }
        }
    }

    @Override
    public String toString(){
        return ""+id+". "+this.dest+" ("+this.date+")";
    }

    public int getId() {
        return id;
    }

    public String getDest() {
        return dest;
    }

    public String getDate() {
        return date;
    }

    public String getItems() {
        String r="";
        if(items.size()==0)
            return r = "e";
        for (int i = 0; i<items.size();i++) {
            r = r + items.get(i).getName() + "_" + items.get(i).getCheckStatus() + "-";
        }
        return r;
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
