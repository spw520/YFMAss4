package com.Saves;

import java.util.ArrayList;

import com.misc.Constants.FortressType;
import com.misc.Constants.TruckType;

/** @author Alex Dawson
 * GameData is the main place where all of the save data is stored so it can easily be
 * saved to a file and information can easily be read back. it consists of getters and setterss
 * for score, time and difficulty. it also handles the lists of trucks and fortresses and 
 * the current truck
 */

public class GameData {
    //private GameState gameState;
    private ArrayList<TruckData> trucks;
    private ArrayList<ETFortressData> fortresses;
    private int score;
    private int time;
    private int difficulty;

    public void saveScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public void saveTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public void saveDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    public int getDifficulty(){
        return difficulty;
    }
    
    public ArrayList<TruckData> loadEngines(){
        return trucks;
    }

    public void saveTrucks(ArrayList<TruckData> trucks){
        this.trucks = trucks;
    }

    public ArrayList<ETFortressData> loadFortresses(){
        return fortresses;
    }

    public void saveFortresses(ArrayList<ETFortressData> fortresses){
        this.fortresses = fortresses;
    }

    public TruckData getTruck(TruckType type){
        for (TruckData truck : this.trucks){
            if (truck.getType() == type){
                return truck;
            }
        }
        //always defaults to first truck, this should never be used
        return this.trucks.get(0);
    }
    
    public ETFortressData getFortress(FortressType type){
        for (ETFortressData fortress : this.fortresses){
            if (fortress.getType() == type){
                return fortress;
            }
        }
        //always defaults to first truck, this should never be used
        return this.fortresses.get(0);
    }
}
