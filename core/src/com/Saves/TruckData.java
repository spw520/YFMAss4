package com.Saves;

import com.misc.Constants.TruckType;

public class TruckData {

    //private Vector2 position;
    private float health;
    private float water;
    private TruckType type;
    private Boolean isBought;



    //public void savePosition(){
    //}

    public void saveHP(float hp){
        health = hp;
    }

    public void saveWater(float wtr){
        water = wtr;
    }

    public void saveType(TruckType typ){
        type = typ;
    }

    public void saveBought(boolean bought){
        isBought = bought;
    }
}