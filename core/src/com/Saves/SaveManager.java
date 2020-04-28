package com.Saves;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.entities.ETFortress;
import com.entities.Firetruck;
import com.screens.GameScreen;

/** @author Alex Dawson
 * SaveManager is the class that actually handles the saving and loading of the data
 * It uses Json to do this. It also saves and pulls from the destination decided by gamePath
 */

public class SaveManager {

    private static Json json = new Json();
    private String gamePath;

    //constructs the manager using the gamePath
    public SaveManager(String gamePath){
        this.gamePath = gamePath;
    }

    public static GameData loadGame(String path) {
        // if the path is empty then do not attempt to read from the file
        // this occurs when the game is launched not from a save
        // if the path is not empty then read the data from the file
        if (path != ""){
            FileHandle fileHandle = new FileHandle(path);
            if (fileHandle.exists()) {
                return json.fromJson(GameData.class, fileHandle.readString());
            }
        }
        return null;
    }
    
    public static void saveGame(GameScreen gameScreen, FileHandle fileHandle) {
        /** @author Alex Dawson when the saveGame funciton is called it creates a new GameData, the class used to save
        * all of the relevant information. the setters are used to set the game screens current values
        * as the values in the save file
        * they are then all written the file dictated by the path. the pretty Print just makes this easier to read
        */
        GameData gameData = new GameData();
        gameData.saveScore(gameScreen.getScore());
        gameData.saveDifficulty(gameScreen.getDifficulty());
        gameData.saveTime(gameScreen.getTime());
        gameData.saveTrucks(getTruckData(gameScreen));
        gameData.saveFortresses(getFortressData(gameScreen));

        fileHandle.writeString(json.prettyPrint(gameData), false);
    }

        /** @author Alex Dawson
         * this creates an array list of all of the trucks so that they can be stored
         * in game data. there is a for loop for the trucks that are currently in the car park
         * and thje first section deals with the current truck that is in use
         */
    private static ArrayList<TruckData> getTruckData(GameScreen gameScreen) {
        ArrayList<TruckData> trucks = new ArrayList<TruckData>();
        TruckData truckData = new TruckData();
        truckData.savePositionX(gameScreen.getActiveTruck().getX());
        truckData.savePositionY(gameScreen.getActiveTruck().getY());
        truckData.saveHealth(gameScreen.getActiveTruck().getHealthBar().getCurrentAmount());
        truckData.saveWater(gameScreen.getActiveTruck().getWaterBar().getCurrentAmount());
        truckData.saveType(gameScreen.getActiveTruck().getType());
        truckData.saveBought(gameScreen.getActiveTruck().isBought());
        trucks.add(truckData);
 
        for (Firetruck firetruck : gameScreen.getFirestation().getParkedFireTrucks()) {
            TruckData truckData2 = new TruckData();
            truckData2.savePositionX(firetruck.getX());
            truckData2.savePositionY(firetruck.getY());
            truckData2.saveHealth((int)firetruck.getHealthBar().getCurrentAmount());
            truckData2.saveWater(firetruck.getWaterBar().getCurrentAmount());
            truckData2.saveType(firetruck.getType());
            truckData2.saveBought(firetruck.isBought());
            trucks.add(truckData2);
        }
        return trucks;
    }
    
    /** @author Alex Dawson
     * creates an array list of all the fortresses to be saved into game data
     */
    private static ArrayList<ETFortressData> getFortressData(GameScreen gameScreen){
        ArrayList<ETFortressData> fortresses = new ArrayList<ETFortressData>();
        for (ETFortress fortress : gameScreen.getFortresses()){
            ETFortressData etfortressData = new ETFortressData();
            etfortressData.saveHP(fortress.getHealthBar().getCurrentAmount());
            etfortressData.saveType(fortress.getType());
            fortresses.add(etfortressData);
        }
        return fortresses;
    }  
}
