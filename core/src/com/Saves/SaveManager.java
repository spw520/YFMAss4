package com.Saves;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.entities.ETFortress;
import com.entities.Firetruck;
import com.screens.GameScreen;

public class SaveManager {

    private static Json json = new Json();

    public static GameData loadGame(FileHandle fileHandle) {
        if (fileHandle.exists()) {
            return json.fromJson(GameData.class, Base64Coder.decodeString(fileHandle.readString()));
        } else {
            return null;
        }
    }
    
    public static void saveGame(GameScreen gameScreen, FileHandle fileHandle) {

        GameData gameData = new GameData();

        gameData.saveTrucks(getTruckData(gameScreen));
        //gameData.saveFortresses(getFortressData(gameScreen));
        

        fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);
    }

    private static ArrayList<TruckData> getTruckData(GameScreen gameScreen) {
        ArrayList<TruckData> trucks = new ArrayList<TruckData>();
        for (Firetruck firetruck : gameScreen.getFirestation().getParkedFireTrucks()) {
            TruckData truckData = new TruckData();
            
            //truckData.savePosition(firetruck.getPosition());
            truckData.saveHP(firetruck.getHealthBar().getCurrentAmount());
            truckData.saveWater(firetruck.getWaterBar().getCurrentAmount());
            truckData.saveType(firetruck.getType());
            truckData.saveBought(firetruck.isBought());
            trucks.add(truckData);
        }
        return trucks;
    }
    
    /*private static ArrayList<ETFortressData> getFortressData(GameScreen gameScreen){
        ArrayList<ETFortressData> fortresses = new ArrayList<ETFortressData>();
        for (ETFortress fortress : gameScreen.getETFortresses()){
            ETFortressData etfortressData = new ETFortressData();
            etfortressData.saveHP(fortress.getHP());
            
        }
    }
    */

    
}
