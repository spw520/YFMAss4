package com.Saves;

import com.misc.Constants.FortressType;

/**
 * @author Alex Dawson
 * ETFortressData is a new class that is used to save the important information about
 * the fortresses. It mainly consists of getters and setters.
 */
public class ETFortressData {

    private float hp;
    private FortressType type;

   public void saveHP(float hp){
        this.hp = hp;
   }

   public void saveType(FortressType type){
        this.type = type;
   }

   public float getHP(){
        return this.hp;
   }

   public FortressType getType(){
        return this.type;
   }
}