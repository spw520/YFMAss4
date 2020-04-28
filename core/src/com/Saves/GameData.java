package com.Saves;

import java.util.ArrayList;

public class GameData {
    //private GameState gameState;
    private ArrayList<TruckData> trucks;
    private ArrayList<ETFortressData> fortresses;

    
    //public GameState getGameState(){
    //    return gameState();
    //}

    //public setGameState(GameState gameState){
        //this.gameState = gameState;
    //}

    
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

    
}
