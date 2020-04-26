package com.misc;

import java.nio.file.Files;

public class Saving {


    private int saveNumber;
    private Gson gson;
    private SaveFile saveFile;
    

    public class SaveFile{
        public boolean empty;

        public int score;

        public int time;

        public int difficulty;

        public int firestationHealth;

        
    }

    public class SavedFiretruck{
        public Constants.TruckType type;
        public int health;
        public int water;
        public boolean isBought;

        public SavedFiretruck(){};

        public SavedFiretruck(Constants.TruckType type, int health, int water, boolean isBought){
            this.health = health;
            this.water = water;
            this.type = type;
            this.isBought = isBought;
        }
    }

    public class SavedFortress{
        public Constants.FortressType type;
        public int health;
        public int flooded;

    }


    public SaveFile() {
        this.empty = true;
    }

    public SaveFile(int score, int time, int difficulty, int firestationHealth){
        this.health = health;
        this.water = water;
        this.type = type;
        this.isBought = isBought;
    }



    private void writeSaveToFile(int saveNumber, SaveFile saveFile){
        try {Writer writer = new FileWriter("Save" + saveNumber + ".json") {
            this.gson.toJson(saveFile, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private SaveFile readFromSaveFile(int saveNumber) throws IOException {
        Path paths = Paths.get("Save" + saveNumber + ".json");
        Reader reader =  Files.newBufferedReader(paths);
        SaveFile savefile = this.gson.fromJson(reader, SaveFile.class);
        reader.close();
        return savefile;
    }


    public SaveFile getSaveFile(){
        return this.saveFile;
    }

    public int getDifficulty(){
        return this.saveFile.difficulty;
    }   

    
}

