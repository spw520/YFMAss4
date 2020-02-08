package com.PathFinding;

import com.badlogic.gdx.math.Vector2;

public class Junction {
    float x;
    float y;
    String name;

    int index;

    /** Constructor for Junction
     *
     * @param x     The x position of the junction in pixels
     * @param y     The y position of the junction in pixels
     * @param name  Descriptor of junction location on the map
     *              - used only for help debugging
     */
    public Junction (float x, float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }


    public void setIndex(int index){
        this.index = index;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public String getName(){
        return this.name;
    }


}
