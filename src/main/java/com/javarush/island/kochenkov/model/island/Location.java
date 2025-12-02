package com.javarush.island.kochenkov.model.island;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final int x;
    private final int y;
    private final List<Animal> animals;
    private final Plant plant;
    private final ReentrantLock lock;
    private final Island island;

    public Location(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.animals = new CopyOnWriteArrayList<>();
        this.plant = new Plant();
        this.lock = new ReentrantLock();
        this.island = island;
    }

    public void addAnimal(Animal animal) {
        lock.lock();
        try {
            long sameTypeCount = animals.stream()
                    .filter(a->a.getType() == animal.getType())
                    .count();
            if(sameTypeCount < animal.getMaxPerLocation()) {
                animals.add(animal);
                animal.setLocation(this);
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeAnimal(Animal animal) {
        lock.lock();
        try {
            animals.remove(animal);
        } finally {
            lock.unlock();
        }
    }

    public List<Animal> getAnimals(){
        return new ArrayList<>(animals);
    }

    public Plant getPlant() {
        return plant;
    }

    public List<Location> getAdjacentLocations() {
        List<Location> adjacent = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0,-1}, {0, 1}};
        for(int[] dir: directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if(island.isValidCoordinate(newX, newY)) {
                adjacent.add(island.getLocation(newX, newY));
            }
        }
        return adjacent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }
}
