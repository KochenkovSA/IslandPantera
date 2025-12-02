package com.javarush.island.kochenkov.simulation;

import com.javarush.island.kochenkov.model.island.Island;
import com.javarush.island.kochenkov.model.island.Location;

public class PlantTask implements Runnable{
    private final Island island;

    public PlantTask(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                location.getPlant().grow();
            }
        }
    }
}
