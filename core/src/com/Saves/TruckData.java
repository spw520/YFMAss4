package com.Saves;

import com.misc.Constants.TruckType;


/**
 * @author Alex Dawson
 * TruckData is a new class that is used to save the important information about
 * the firetrucks. It mainly consists of getters and setters.
 */

public class TruckData {

    private float health;
    private float water;
    private TruckType type;
    private Boolean isBought;
    private float x,y;


    public void savePositionX(float posX){
        this.x = posX;
    }

    public void savePositionY(float posY){
        this.y = posY;
    }

    public void saveHealth(float hp){
        health = hp;
    }

    public void saveWater(float wtr){
        water = wtr;
    }

    public float getWater(){
        return this.water;
    }

    public void saveType(TruckType typ){
        type = typ;
    }

    public void saveBought(boolean bought){
        isBought = bought;
    }

    public boolean getBought(){
        return this.isBought;
    }

    public TruckType getType(){
        return this.type;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getHealth(){
        return this.health;
    }
}