package ch.andefgassm.adventuregame.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Savegame {

    private int positionX = 3;
    private int positionY = 19;
    private List<String> killedBosses = new ArrayList<String>();
    private List<String> inventoryItems = new ArrayList<String>();
    private List<String> equippedItems = new ArrayList<String>();

    private static final String FILENAME = "savegame.json";

    public static Savegame load() {
        ObjectMapper mapper = new ObjectMapper();
        Savegame savegame = null;
        File f = new File(FILENAME);
        if (!f.exists()) { // if savegame file doesnt exist, load default values
            return new Savegame();
        }
        try {
            savegame = mapper.readValue(new File(FILENAME), Savegame.class);
        } catch (Exception e) {
            throw new AdventureGameException("Failed to load savegame", e);
        }
        return savegame;
    }

    public static void save(Savegame savegame) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(FILENAME), savegame);
        } catch (Exception e) {
            throw new AdventureGameException("Failed to save savegame", e);
        }
    }

    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public List<String> getKilledBosses() {
        return killedBosses;
    }
    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    public List<String> getEquippedItems() {
        return equippedItems;
    }




}
