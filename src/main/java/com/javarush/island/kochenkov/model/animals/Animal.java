package com.javarush.island.kochenkov.model.animals;

import com.javarush.island.kochenkov.model.enums.AnimalType;
import com.javarush.island.kochenkov.model.island.Location;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    protected final AnimalType type;
    protected double weight;
    protected double maxWeight;
    protected int maxPerLocation;
    protected int speed;
    protected double foodNeeded;
    protected boolean isAlive = true;
    protected Location location;
    protected double satiety = 0;

    public Animal(AnimalType type, double weight, int maxPerLocation, int speed, double foodNeeded) {
        this.type = type;
        this.weight = weight;
        this.maxWeight = weight;
        this.maxPerLocation = maxPerLocation;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
    }

    public abstract boolean eat(Object food);

    public abstract void multiply();

    public abstract Location chooseDirection();

    public void move(Location newLocation) {
        if (location != null) {
            location.removeAnimal(this);
        }
        this.location = newLocation;
        newLocation.addAnimal(this);
    }

    public void die() {
        isAlive = false;
        if (location != null) {
            location.removeAnimal(this);
        }
    }

    public void reduceSatiety() {
        satiety = Math.max(0, satiety - foodNeeded * 0.3);
        if (satiety <= 0) {
            weight -= maxWeight*0.05;
            if(weight <= maxWeight *0.3) {
                die();
            }
        }
    }

    public AnimalType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxPerLocation() {
        return maxPerLocation;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodNeeded() {
        return foodNeeded;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getSatiety() {
        return satiety;
    }

    public void setSatiety(double satiety) {
        this.satiety = satiety;
    }

    protected boolean tryToEat(double probability) {
        return ThreadLocalRandom.current().nextDouble(100) < probability;
    }
}
